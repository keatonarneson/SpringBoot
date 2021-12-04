package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.service.LikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultLikeController implements LikeController {

  @Autowired
  LikeService likeService;

  @Override
  public Post likePost(Long userId, Long postId) {

    return likeService.likePost(postId, userId);
  }

  @Override
  public Post unlikePost(Long userId, Long postId) {

    return likeService.unlikePost(postId, userId);
  }

  @Override
  public List<Like> getLikesForPost(Long postId) {

    return likeService.getLikesForPost(postId);
  }
  
}
