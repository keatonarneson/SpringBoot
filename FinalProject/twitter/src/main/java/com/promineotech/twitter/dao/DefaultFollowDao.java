package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.UserService;

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

  @Autowired
  private UserService userService;

  @Override
  public Follower followUser(Long userId, Long authUserId) {

    userService.checkIfUserExists(userId);
    userService.checkIfUserExists(authUserId);
    checkIfUserAndAuthUserAreEqual(userId, authUserId);
    checkIfFollowerExists(userId, authUserId, false);
    
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

    checkIfFollowerExists(userId, authUserId, true);
    
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

    userService.checkIfUserExists(userId);

    String sql = "SELECT followers.*, users.* FROM followers INNER JOIN users ON following_user = users.user_id WHERE follower_user = :follower_user";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("follower_user", userId);

    return jdbcTemplate.query(params.sql, params.source, new UserMapper());

  }

  @Override
  public List<User> getFollowingForUser(Long userId) {

    userService.checkIfUserExists(userId);

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
        return null;
      }

      return Follower.builder()
          .followerId(rs.getLong("follower_id"))
          .followerUser(userDao.getUserById(rs.getLong("follower_user")))
          .followingUser(userDao.getUserById(rs.getLong("following_user")))
          .followedOn(rs.getDate("followed_on"))
          .build();
    }
  }

  private void checkIfFollowerExists(Long userId, Long authUserId, boolean unfollow) {

    String sql = "SELECT * FROM followers where follower_user = :follower_user AND following_user = :following_user";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("follower_user", userId);
    params.source.addValue("following_user", authUserId);

    Follower follower = jdbcTemplate.query(params.sql, params.source, new FollowingResultSetExtractor());

    if (follower != null && unfollow == false) {
      String msg = String.format("User with UserID=%d already follows User with UserID=%d", authUserId, userId);

      throw new IllegalArgumentException(msg);
    }

    if (follower == null && unfollow == true) {
      String msg = String.format("User with UserID=%d does not follow User with UserID=%d", authUserId, userId);

      throw new IllegalArgumentException(msg);
    }
  }
  
  private void checkIfUserAndAuthUserAreEqual(Long userId, Long authUserId) {
    if (userId == authUserId) {
      String msg = String.format("User with UserID=%d cannot follow User with UserID=%d as they are the same User", authUserId, userId);

      throw new IllegalArgumentException(msg);
    }
  }
}






