package com.promineotech.twitter.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDao implements UserDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public List<User> getAllUsers() {
    String sql = "SELECT * FROM users";

    return jdbcTemplate.query(sql, new UserMapper());
  }

  @Override
  public User getUserById(Long userId) {
    String sql = "SELECT * FROM users WHERE user_id = :user_id";

    Map<String, Object> params = new HashMap<>();
    params.put("user_id", userId);

    return jdbcTemplate.query(sql, params, new UserResultSetExtractor());

  }

  @Override
  public User saveUser(User user) {
    String sql = "INSERT INTO users (username, email, password, name, bio, location, website, birthdate, avatar_url, cover_photo_url, followers_count, following_count) VALUES (:username, :email, :password, :name, :bio, :location, :website, :birthdate, :avatar_url, :cover_photo_url, :followers_count, :following_count)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("username", user.getUsername());
    params.source.addValue("email", user.getEmail());
    params.source.addValue("password", user.getPassword());
    params.source.addValue("name", user.getName());
    params.source.addValue("bio", user.getBio());
    params.source.addValue("location", user.getLocation());
    params.source.addValue("website", user.getWebsite());
    params.source.addValue("birthdate", user.getBirthdate());
    params.source.addValue("avatar_url", user.getAvatarURL());
    params.source.addValue("cover_photo_url", user.getCoverPhotoURL());
    params.source.addValue("followers_count", user.getFollowersCount());
    params.source.addValue("following_count", user.getFollowingCount());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long userId = keyHolder.getKey().longValue();

    return User.builder().userId(userId).username(user.getUsername()).email(user.getEmail())
        .password(user.getPassword()).name(user.getName()).bio(user.getBio()).location(user.getLocation())
        .website(user.getWebsite()).birthdate(user.getBirthdate()).avatarURL(user.getAvatarURL())
        .coverPhotoURL(user.getCoverPhotoURL()).followersCount(user.getFollowersCount())
        .followingCount(user.getFollowingCount()).registeredOn(user.getRegisteredOn()).build();
  }

  @Override
  public User updateUser(User user, Long userId) {
    String sql = "UPDATE users SET username=:username, email=:email, password=:password, name=:name, bio=:bio, location=:location, website=:website, birthdate=:birthdate, avatar_url=:avatar_url, cover_photo_url=:cover_photo_url, followers_count=:followers_count, following_count=:following_count WHERE user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("username", user.getUsername());
    params.source.addValue("email", user.getEmail());
    params.source.addValue("password", user.getPassword());
    params.source.addValue("name", user.getName());
    params.source.addValue("bio", user.getBio());
    params.source.addValue("location", user.getLocation());
    params.source.addValue("website", user.getWebsite());
    params.source.addValue("birthdate", user.getBirthdate());
    params.source.addValue("avatar_url", user.getAvatarURL());
    params.source.addValue("cover_photo_url", user.getCoverPhotoURL());
    params.source.addValue("followers_count", user.getFollowersCount());
    params.source.addValue("following_count", user.getFollowingCount());
    params.source.addValue("user_id", userId);

    jdbcTemplate.update(params.sql, params.source);

    return User.builder().userId(user.getUserId()).username(user.getUsername()).email(user.getEmail())
        .password(user.getPassword()).name(user.getName()).bio(user.getBio()).location(user.getLocation())
        .website(user.getWebsite()).birthdate(user.getBirthdate()).avatarURL(user.getAvatarURL())
        .coverPhotoURL(user.getCoverPhotoURL()).followersCount(user.getFollowersCount())
        .followingCount(user.getFollowingCount()).build();
  }

  @Override
  public void deleteUser(Long userId) {
    String sql = "DELETE FROM users WHERE user_id=:user_id";

    Map<String, Object> params = new HashMap<>();
    params.put("user_id", userId);

    jdbcTemplate.update(sql, params);
  }

}

// --------------------helper methods below-------------------------------------------------

class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    return User.builder().userId(rs.getLong("user_id")).username(rs.getString("username")).email(rs.getString("email"))
        .password(rs.getString("password")).name(rs.getString("name")).bio(rs.getString("bio"))
        .location(rs.getString("location")).birthdate(rs.getDate("birthdate")).avatarURL(rs.getString("avatar_url"))
        .coverPhotoURL(rs.getString("cover_photo_url")).followersCount(rs.getInt("followers_count"))
        .followingCount(rs.getInt("followers_count")).registeredOn(rs.getDate("registered_on")).build();
  }

}

class UserResultSetExtractor implements ResultSetExtractor<User> {
  @Override
  public User extractData(ResultSet rs) throws SQLException {

    if (!rs.next()) {
      throw new NoSuchElementException();
    }
    

      return User.builder().userId(rs.getLong("user_id")).username(rs.getString("username")).email(rs.getString("email"))
          .password(rs.getString("password")).name(rs.getString("name")).bio(rs.getString("bio"))
          .location(rs.getString("location")).birthdate(rs.getDate("birthdate")).avatarURL(rs.getString("avatar_url"))
          .coverPhotoURL(rs.getString("cover_photo_url")).followersCount(rs.getInt("followers_count"))
          .followingCount(rs.getInt("followers_count")).registeredOn(rs.getDate("registered_on")).build();
    
  }
}

class SqlParams {
  String sql;
  MapSqlParameterSource source = new MapSqlParameterSource();
}
