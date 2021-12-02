package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPostDao implements PostDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public List<Post> getAllPostsForUser(User user) {

    String sql = "SELECT * FROM posts WHERE posted_by = :posted_by";

    Map<String, Object> params = new HashMap<>();
    params.put("posted_by", user.getUserId());

    return jdbcTemplate.query(sql, params, new PostMapper());
  }

  @Override
  public Post getPostById(Long postId) {

    String sql = "SELECT * FROM posts WHERE post_id = :post_id";

    Map<String, Object> params = new HashMap<>();
    params.put("post_id", postId);

    return jdbcTemplate.query(sql, params, new PostResultSetExtractor());
  }

  @Override
  public Post savePost(Post post) {
    
    String sql = "INSERT INTO posts (content, posted_by, like_count, reply_count, parent_post) VALUES (:content, :posted_by, :like_count, :reply_count, :parent_post)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("content", post.getContent());
    params.source.addValue("posted_by", post.getPostedBy());
    params.source.addValue("like_count", post.getLikeCount());
    params.source.addValue("reply_count", post.getReplyCount());
    params.source.addValue("parent_post", post.getParentPost());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long postId = keyHolder.getKey().longValue();

    return Post.builder()
        .postId(postId)
        .content(post.getContent())
        .postedBy(post.getPostedBy())
        .likeCount(post.getLikeCount())
        .replyCount(post.getReplyCount())
        .parentPost(post.getParentPost())
        .build();
  }

  @Override
  public void deletePost(Long postId) {
    
    String sql = "DELETE FROM posts WHERE post_id = :post_id";

    Map<String, Object> params = new HashMap<>();
    params.put("post_id", postId);

    jdbcTemplate.update(sql, params);
  }

  @Override
  public User fetchUser(Long userId) {
    String sql = "SELECT * FROM users WHERE user_id = :user_id";

    Map<String, Object> params = new HashMap<>();
    params.put("user_id", userId);
    return jdbcTemplate.query(sql, params, new UserResultSetExtractor());
  }

  // ---------------------------------------------------------------------------------------
  class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {

      return Post.builder().postId(rs.getLong("post_id")).content(rs.getString("content"))
          .postedBy(fetchUser(rs.getLong("posted_by")))
          .postedOn(rs.getDate("posted_on")).likeCount(rs.getInt("like_count"))
          .parentPost(getPostById(rs.getLong("post_id"))).build();
    }
  }
  
  class PostResultSetExtractor implements ResultSetExtractor<Post> {

    @Override
    public Post extractData(ResultSet rs) throws SQLException, DataAccessException {
      
      if (!rs.next()) {
        throw new NoSuchElementException();
      }

      return Post.builder().postId(rs.getLong("post_id")).content(rs.getString("content"))
          .postedBy(fetchUser(rs.getLong("posted_by")))
          .postedOn(rs.getDate("posted_on")).likeCount(rs.getInt("like_count"))
          .parentPost(null).build();
    }
    
  }

}


