package com.promineotech.twitter.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.promineotech.twitter.dao.PostDao;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultPostService implements PostService {

  @Autowired
  private PostDao postDao;

  @Transactional(readOnly = true)
  @Override
  public List<Post> getAllPostsForUser(Long userId) {

    User user = getUser(userId);
    
    return postDao.getAllPostsForUser(user);
  }

  @Transactional(readOnly = true)
  @Override
  public Post getPostById(Long postId) {
    
    return postDao.getPostById(postId);
  }

  @Transactional
  @Override
  public Post createPost(Post post) {

    return postDao.savePost(post);
  }

  @Transactional
  @Override
  public void deletePost(Long postId) {
    
    postDao.deletePost(postId);
  }

  // ---------------------------------------------------------------------------------------
  
  private User getUser(Long userId) {
    return postDao.fetchUser(userId);
  }
  
}
