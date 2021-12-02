package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.dao.LikeDao;
import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultLikeService implements LikeService {

  @Autowired
  private LikeDao likeDao;

  @Transactional
  @Override
  public Post likePost(Long postId, User likedUser) {

    return likeDao.likePost(postId, likedUser);
  }

  @Transactional
  @Override
  public Post unlikePost(Long postId, User likedUser) {

    return likeDao.unlikePost(postId, likedUser);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Like> getLikesForPost(Long postId) {

    return likeDao.getLikesForPost(postId);
  }
  
}
