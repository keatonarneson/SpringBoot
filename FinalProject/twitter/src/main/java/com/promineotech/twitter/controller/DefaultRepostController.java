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
  public Post addRepost(User authUser, Long postId) {

    return repostService.addRepost(postId, authUser);
  }

  @Override
  public void removeRepost(User authUser, Long postId) {

    repostService.removeRepost(postId, authUser);
  }
  
}
