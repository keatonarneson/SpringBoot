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

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/v1")
public interface FollowController {

  @Operation(summary = "Follow a User", description = "Follow a User by User ID")
  @PostMapping("users/follow/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  Follower followUser(@RequestBody User authUser, @PathVariable Long userId);

  @Operation(summary = "Unfollow a User", description = "Unfollow a User by User ID")
  @DeleteMapping("users/unfollow/{userId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  void unfollowUser(@RequestBody User authUser, @PathVariable Long userId);

  @Operation(summary = "Get all Followers for User", description = "Returns a list of Users who are Followers of User ID")
  @GetMapping("users/followers/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowersForUser(@PathVariable Long userId);

  @Operation(summary = "Get all Followings for User", description = "Returns a list of Users who are Following of User ID")
  @GetMapping("users/following/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowingForUser(@PathVariable Long userId);
  
}
