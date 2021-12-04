package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.dao.LikeDao;
import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultLikeService implements LikeService {

  @Autowired
  private LikeDao likeDao;

  @Autowired
  private PostService postService;

  @Transactional
  @Override
  public Post likePost(Long postId, Long likedUserId) {

    return likeDao.likePost(postId, likedUserId);
  }

  @Transactional
  @Override
  public Post unlikePost(Long postId, Long likedUserId) {

    return likeDao.unlikePost(postId, likedUserId);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Like> getLikesForPost(Long postId) {

    postService.checkIfPostExists(postId);

    return likeDao.getLikesForPost(postId);
  }
}
