package com.promineotech.twitter.dao;

import java.util.List;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;

public interface FollowDao {
  
  Follower followUser(Long userId, Long authUserId);

  void unfollowUser(Long userId, Long authUserId);

  List<User> getFollowersForUser(Long userId);

  List<User> getFollowingForUser(Long userId);
}
