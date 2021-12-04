package com.promineotech.twitter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.Repost;
import com.promineotech.twitter.service.PostService;
import com.promineotech.twitter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultRepostDao implements RepostDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  private PostDao postDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @Override
  public Post addRepost(Long postId, Long authUserId) {

    userService.checkIfUserExists(authUserId);
    postService.checkIfPostExists(postId);
    checkIfRepostExists(postId, authUserId, false);

    String sql = "INSERT INTO reposts (post_id, user_id) VALUES (:post_id, :user_id)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", authUserId);

    jdbcTemplate.update(params.sql, params.source);

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
  public void removeRepost(Long postId, Long authUserId) {

    checkIfRepostExists(postId, authUserId, true);

    String sql = "DELETE FROM reposts WHERE post_id = :post_id AND user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", authUserId);

    jdbcTemplate.update(params.sql, params.source);
  }

  //----------------------------------------------------------------------------------------

  class RepostResultSetExtractor implements ResultSetExtractor<Repost> {

    @Override
    public Repost extractData(ResultSet rs) throws SQLException {

    if (!rs.next()) {
      return null;
    }

      return Repost.builder()
          .repostId(rs.getLong("repost_id"))
          .post(postDao.getPostById(rs.getLong("post_id")))
          .user(userDao.getUserById(rs.getLong("user_id")))
          .repostedOn(rs.getDate("reposted_on"))
          .build();

    }
  }

  private void checkIfRepostExists(Long postId, Long authUserId, Boolean removeRepost) {

    String sql = "SELECT * FROM reposts where post_id = :post_id AND user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", authUserId);

    Repost repost = jdbcTemplate.query(params.sql, params.source, new RepostResultSetExtractor());

    if (repost != null && removeRepost == false) {
      String msg = String.format("User with UserID=%d already reposted Post with PostID=%d", authUserId, postId);

      throw new IllegalArgumentException(msg);
    }

    if (repost == null && removeRepost == true) {
      String msg = String.format("Repost for User with UserID=%d and Post with PostID=%d does not exist", authUserId,
          postId);

      throw new IllegalArgumentException(msg);
    }
  }
}
