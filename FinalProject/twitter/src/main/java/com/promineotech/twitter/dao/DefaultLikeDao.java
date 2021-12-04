package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultLikeDao implements LikeDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  private PostDao postDao;

  @Autowired
  private UserDao userDao;

  @Override
  public Post likePost(Long postId, Long likedUserId) {

    checkIfLikeExists(likedUserId, postId, false);

    String sql = "INSERT INTO likes (post_id, user_id) VALUES (:post_id, :user_id)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", likedUserId);

    jdbcTemplate.update(params.sql, params.source);

    String sqlCount = "UPDATE posts SET like_count = like_count + 1 WHERE post_id = :post_id";

    SqlParams paramsCount = new SqlParams();

    paramsCount.sql = sqlCount;
    paramsCount.source.addValue("post_id", postId);

    jdbcTemplate.update(paramsCount.sql, paramsCount.source);

    Post post = postDao.getPostById(postId);

    return Post.builder()
        .postId(post.getPostId())
        .content(post.getContent())
        .postedBy(post.getPostedBy())
        .postedOn(post.getPostedOn())
        .likeCount(post.getLikeCount())
        .replyCount(post.getReplyCount())
        .parentPost(post.getParentPost())
        .build();
  }

  @Override
  public Post unlikePost(Long postId, Long likedUserId) {
    
    checkIfLikeExists(likedUserId, postId, true);

    String sql = "DELETE FROM likes WHERE post_id = :post_id AND user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", likedUserId);

    jdbcTemplate.update(params.sql, params.source);

    String sqlCount = "UPDATE posts SET like_count = like_count - 1 WHERE post_id = :post_id";

    SqlParams paramsCount = new SqlParams();

    paramsCount.sql = sqlCount;
    paramsCount.source.addValue("post_id", postId);

    jdbcTemplate.update(paramsCount.sql, paramsCount.source);

    Post post = postDao.getPostById(postId);

    return Post.builder()
        .postId(post.getPostId())
        .content(post.getContent())
        .postedBy(post.getPostedBy())
        .postedOn(post.getPostedOn())
        .likeCount(post.getLikeCount())
        .replyCount(post.getReplyCount())
        .parentPost(post.getParentPost())
        .build();
  }

  @Override
  public List<Like> getLikesForPost(Long postId) {

    String sql = "SELECT * FROM likes WHERE post_id = :post_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);

    return jdbcTemplate.query(params.sql, params.source, new LikeMapper());
  }

  class LikeMapper implements RowMapper<Like> {

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {

      return Like.builder()
          .likeId(rs.getLong("like_id"))
          .post(postDao.getPostById(rs.getLong("post_id")))
          .user(userDao.getUserById(rs.getLong("user_id")))
          .likedOn(rs.getDate("liked_on"))
          .build();
    }

  }

  //----------------------------------------------------------------------------------------

  class LikeResultSetExtractor implements ResultSetExtractor<Like> {
    @Override
    public Like extractData(ResultSet rs) throws SQLException {

    if (!rs.next()) {
      return null;
    }

      return Like.builder()
          .likeId(rs.getLong("like_id"))
          .post(postDao.getPostById(rs.getLong("post_id")))
          .user(userDao.getUserById(rs.getLong("user_id")))
          .likedOn(rs.getDate("liked_on"))
          .build();

    }
  }

  private void checkIfLikeExists(Long userId, Long postId, Boolean unlikeCheck) {

    String sql = "SELECT * FROM likes where post_id = :post_id AND user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", userId);

    Like like = jdbcTemplate.query(params.sql, params.source, new LikeResultSetExtractor());

    if (like != null && unlikeCheck == false) {
      String msg = String.format("User with UserID=%d already liked Post with PostID=%d", userId, postId);

      throw new IllegalArgumentException(msg);
    }

    if (like == null && unlikeCheck == true) {
      String msg = String.format("Like for User with UserID=%d and Post with PostID=%d does not exist", userId, postId);

      throw new IllegalArgumentException(msg);
    }
  }
}

