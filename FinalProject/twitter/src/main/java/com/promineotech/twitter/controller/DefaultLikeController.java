package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.LikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultLikeController implements LikeController {

  @Autowired
  LikeService likeService;

  @Override
  public Post likePost(User user, Long postId) {

    return likeService.likePost(postId, user);
  }

  @Override
  public Post unlikePost(User user, Long postId) {

    return likeService.unlikePost(postId, user);
  }

  @Override
  public List<Like> getLikesForPost(Long postId) {

    return likeService.getLikesForPost(postId);
  }
  
}
