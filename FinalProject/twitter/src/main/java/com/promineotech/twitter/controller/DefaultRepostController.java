package com.promineotech.twitter.controller;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.RepostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRepostController implements RepostController {

  @Autowired
  private RepostService repostService;

  @Override
  public Post addRepost(Long authUserId, Long postId) {

    return repostService.addRepost(postId, authUserId);
  }

  @Override
  public void removeRepost(Long authUserId, Long postId) {

    repostService.removeRepost(postId, authUserId);
  }
  
}
