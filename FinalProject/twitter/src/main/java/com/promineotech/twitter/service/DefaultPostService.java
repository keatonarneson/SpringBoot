package com.promineotech.twitter.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.promineotech.twitter.dao.PostDao;
import com.promineotech.twitter.dao.UserDao;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultPostService implements PostService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PostDao postDao;

  @Transactional(readOnly = true)
  @Override
  public List<Post> getAllPostsForUser(Long userId) {

    User user = userDao.getUserById(userId);

    return postDao.getAllPostsForUser(user);
  }
  
  @Transactional(readOnly = true)
  @Override
  public List<Post> getAllPostsAndRepostsForUser(Long userId) {

    User user = userDao.getUserById(userId);

    return postDao.getAllPostsAndRepostsForUser(user);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Post> getAllPostsAndRepostsAndRepliesForUser(Long userId) {

    User user = userDao.getUserById(userId);

    return postDao.getAllPostsAndRepostsAndRepliesForUser(user);
  }

  @Transactional(readOnly = true)
  @Override
  public Post getPostById(Long postId) {

    checkIfPostExists(postId);
    
    return postDao.getPostById(postId);
  }

  @Transactional
  @Override
  public Post createPost(Post post) {

    return postDao.createPost(post);
  }

  @Transactional
  @Override
  public void deletePost(Long postId) {

    checkIfPostExists(postId);

    postDao.deletePost(postId);
  }

// ---------------------------------------------------------------------------------------
  public void checkIfPostExists(Long postId) {
    Post fetchedPost = postDao.getPostById(postId);

    if (fetchedPost == null) {
      String msg = String.format("No Post with Post ID = %d", postId);

      throw new NoSuchElementException(msg);
    }
  }

  @Override
  public Post getParentPostById(Long postId) {
    
    return postDao.getPostById(postId).getParentPost();
  }
}
