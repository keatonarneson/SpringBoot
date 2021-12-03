package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class DefaultFollowDao implements FollowDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  private UserDao userDao;

  @Override
  public Follower followUser(Long userId, Long authUserId) {
    
    String sql = "INSERT INTO followers (follower_user, following_user) VALUES (:follower_user, :following_user)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("follower_user", userId);
    params.source.addValue("following_user", authUserId);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    String sqlFollower = "UPDATE users SET followers_count = followers_count + 1 WHERE user_id = :user_id";

    SqlParams paramsFollower = new SqlParams();

    paramsFollower.sql = sqlFollower;
    paramsFollower.source.addValue("user_id", userId);

    jdbcTemplate.update(paramsFollower.sql, paramsFollower.source);

    String sqlFollowing = "UPDATE users SET following_count = following_count + 1 WHERE user_id = :user_id";

    SqlParams paramsFollowing = new SqlParams();

    paramsFollowing.sql = sqlFollowing;
    paramsFollowing.source.addValue("user_id", authUserId);

    jdbcTemplate.update(paramsFollowing.sql, paramsFollowing.source);

    Long followId = keyHolder.getKey().longValue();

    String sqlExtractor = "SELECT * FROM followers WHERE follower_id = :follower_id";

    Map<String, Object> paramsExtractor = new HashMap<>();
    paramsExtractor.put("follower_id", followId);

    return jdbcTemplate.query(sqlExtractor, paramsExtractor, new FollowingResultSetExtractor());
  }

  @Override
  public void unfollowUser(Long userId, Long authUserId) {
    
    String sql = "DELETE FROM followers WHERE follower_user = :follower_user AND following_user = :following_user";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("follower_user", userId);
    params.source.addValue("following_user", authUserId);

    jdbcTemplate.update(params.sql, params.source);

    String sqlFollower = "UPDATE users SET followers_count = followers_count - 1 WHERE user_id = :user_id";

    SqlParams paramsFollower = new SqlParams();

    paramsFollower.sql = sqlFollower;
    paramsFollower.source.addValue("user_id", userId);

    jdbcTemplate.update(paramsFollower.sql, paramsFollower.source);

    String sqlFollowing = "UPDATE users SET following_count = following_count - 1 WHERE user_id = :user_id";

    SqlParams paramsFollowing = new SqlParams();

    paramsFollowing.sql = sqlFollowing;
    paramsFollowing.source.addValue("user_id", authUserId);

    jdbcTemplate.update(paramsFollowing.sql, paramsFollowing.source);
  }

  @Override
  public List<User> getFollowersForUser(Long userId) {

    String sql = "SELECT followers.*, users.* FROM followers INNER JOIN users ON following_user = users.user_id WHERE follower_user = :follower_user";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("follower_user", userId);

    return jdbcTemplate.query(params.sql, params.source, new UserMapper());

  }

  @Override
  public List<User> getFollowingForUser(Long userId) {

    String sql = "SELECT followers.*, users.* FROM followers INNER JOIN users ON follower_user = users.user_id WHERE following_user = :following_user";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("following_user", userId);

    return jdbcTemplate.query(params.sql, params.source, new UserMapper());
  }

  // ---------------------------------------------------------------------------------------

  class FollowingResultSetExtractor implements ResultSetExtractor<Follower> {

    @Override
    public Follower extractData(ResultSet rs) throws SQLException, DataAccessException {

      if (!rs.next()) {
        throw new NoSuchElementException();
      }

      return Follower.builder()
          .followerId(rs.getLong("follower_id"))
          .followerUser(userDao.getUserById(rs.getLong("follower_user")))
          .followingUser(userDao.getUserById(rs.getLong("following_user")))
          .followedOn(rs.getDate("followed_on"))
          .build();
    }
  }
}
