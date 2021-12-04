package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.PostService;

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

  @Autowired
  private UserDao userDao;

  @Autowired
  private PostDao postDao;

  @Autowired
  private PostService postService;

  @Override
  public List<Post> getAllPostsForUser(User user) {

    String sql = "SELECT * FROM posts WHERE posted_by = :posted_by AND parent_id IS NULL";

    Map<String, Object> params = new HashMap<>();
    params.put("posted_by", user.getUserId());

    return jdbcTemplate.query(sql, params, new PostMapper());
  }

  @Override
  public List<Post> getAllPostsAndRepostsForUser(User user) {
    
    String sql = "SELECT * FROM posts LEFT JOIN reposts ON posts.post_id = reposts.post_id WHERE posted_by = :posted_by OR user_id = :user_id AND parent_id IS NULL GROUP BY posts.post_id";

    Map<String, Object> params = new HashMap<>();
    params.put("user_id", user.getUserId());
    params.put("posted_by", user.getUserId());

    return jdbcTemplate.query(sql, params, new PostMapper());
  }

  @Override
  public List<Post> getAllPostsAndRepostsAndRepliesForUser(User user) {

    String sql = "SELECT * FROM posts LEFT JOIN reposts ON posts.post_id = reposts.post_id WHERE posted_by = :posted_by OR user_id = :user_id GROUP BY posts.post_id";

    Map<String, Object> params = new HashMap<>();
    params.put("user_id", user.getUserId());
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
  public Post createPost(Post post) {

    String sql = "INSERT INTO posts (content, posted_by, like_count, reply_count, parent_id) VALUES (:content, :posted_by, :like_count, :reply_count, :parent_id)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("content", post.getContent());
    params.source.addValue("posted_by", post.getPostedBy().getUserId());
    params.source.addValue("like_count", post.getLikeCount());
    params.source.addValue("reply_count", post.getReplyCount());
    
    if (post.getParentPost() == null) {
      params.source.addValue("parent_id", null);
    } else {
      params.source.addValue("parent_id", post.getParentPost().getPostId());

      String sqlCount = "UPDATE posts SET reply_count = reply_count + 1 WHERE post_id = :post_id";

      SqlParams paramsCount = new SqlParams();

      paramsCount.sql = sqlCount;
      paramsCount.source.addValue("post_id", post.getParentPost().getPostId());

      jdbcTemplate.update(paramsCount.sql, paramsCount.source);
    }

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long postId = keyHolder.getKey().longValue();

    return Post.builder()
        .postId(postId)
        .content(post.getContent())
        .postedBy(post.getPostedBy())
        .postedOn(post.getPostedOn())
        .likeCount(post.getLikeCount())
        .replyCount(post.getReplyCount())
        .parentPost(post.getParentPost())
        .build();
  }

  @Override
  public void deletePost(Long postId) {

      if (postService.getParentPostById(postId) != null) {

      String sqlCount = "UPDATE posts SET reply_count = reply_count - 1 WHERE post_id = :post_id";

      SqlParams paramsCount = new SqlParams();

      paramsCount.sql = sqlCount;
      paramsCount.source.addValue("post_id", postService.getPostById(postId).getParentPost().getPostId());

      jdbcTemplate.update(paramsCount.sql, paramsCount.source);
    }

    String sql = "DELETE FROM posts WHERE post_id = :post_id";

    Map<String, Object> params = new HashMap<>();
    params.put("post_id", postId);

    jdbcTemplate.update(sql, params);
  }

// ---------------------------------------------------------------------------------------
class PostResultSetExtractor implements ResultSetExtractor<Post> {

  @Override
  public Post extractData(ResultSet rs) throws SQLException, DataAccessException {

    if (!rs.next()) {
      throw new NoSuchElementException();
    }

    Long parentId;

    Post parentPost = null;

    if (rs.getLong("parent_id") > 0) {
        parentId = rs.getLong("parent_id");
        parentPost = postDao.getPostById(parentId);
    }

    return Post.builder().postId(rs.getLong("post_id")).content(rs.getString("content"))
        .postedBy(userDao.getUserById(rs.getLong("posted_by")))
        .postedOn(rs.getDate("posted_on")).likeCount(rs.getInt("like_count")).replyCount(rs.getInt("reply_count")).parentPost(parentPost).build();
  }
}


class PostMapper implements RowMapper<Post> {

  @Override
  public Post mapRow(ResultSet rs, int rowNum) throws SQLException {

    Long parentId;

    Post parentPost = null;

    if (rs.getLong("parent_id") > 0) {
      parentId = rs.getLong("parent_id");
      parentPost = postDao.getPostById(parentId);
    }

    return Post.builder().postId(rs.getLong("post_id")).content(rs.getString("content"))
      .postedBy(userDao.getUserById(rs.getLong("posted_by")))
      .postedOn(rs.getDate("posted_on")).likeCount(rs.getInt("like_count"))
      .replyCount(rs.getInt("reply_count")).parentPost(parentPost).build();
    }
  }
}


  



