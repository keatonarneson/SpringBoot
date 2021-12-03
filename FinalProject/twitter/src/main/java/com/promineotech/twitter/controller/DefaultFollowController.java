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
  public Follower followUser(Long authUserId, Long userId) {

    return followService.followUser(userId, authUserId);
  }

  @Override
  public void unfollowUser(Long authUserId, Long userId) {

    followService.unfollowUser(userId, authUserId);
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
