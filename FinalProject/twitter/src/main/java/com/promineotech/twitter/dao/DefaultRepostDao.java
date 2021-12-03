package com.promineotech.twitter.dao;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultRepostDao implements RepostDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  private PostDao postDao;

  @Override
  public Post addRepost(Long postId, User authUser) {
    
    String sql = "INSERT INTO reposts (post_id, user_id) VALUES (:post_id, :user_id)";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", authUser.getUserId());

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
  public void removeRepost(Long postId, User authUser) {
    
    String sql = "DELETE FROM reposts WHERE post_id = :post_id AND user_id = :user_id";

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("post_id", postId);
    params.source.addValue("user_id", authUser.getUserId());

    jdbcTemplate.update(params.sql, params.source);
  }
  
}
