package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultFollowController implements FollowController {

  @Autowired
  FollowService followService;

  @Override
  public Follower followUser(User authUser, Long userId) {

    return followService.followUser(userId, authUser);
  }

  @Override
  public void unfollowUser(User authUser, Long userId) {

    followService.unfollowUser(userId, authUser);
  }

  @Override
  public List<User> getFollowersForUser(Long userId) {

    return followService.getFollowersForUser(userId);
  }

  @Override
  public List<User> getFollowingForUser(Long userId) {

    return followService.getFollowingForUser(userId);
  }
  
}
