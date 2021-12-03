package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1")
public interface FollowController {

  @PostMapping("users/follow/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  Follower followUser(@RequestBody User authUser, @PathVariable Long userId);

  @DeleteMapping("users/unfollow/{userId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  void unfollowUser(@RequestBody User authUser, @PathVariable Long userId);

  @GetMapping("users/followers/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowersForUser(@PathVariable Long userId);

  @GetMapping("users/following/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowingForUser(@PathVariable Long userId);
  
}
