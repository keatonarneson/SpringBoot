package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;
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
public interface LikeController {

  @Operation(summary = "Like a Post", description = "Like a Post and returns the Post")
  @PostMapping("posts/like/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post likePost(@RequestBody User user, @PathVariable Long postId);

  @Operation(summary = "Unlike a Post", description = "Unlike a Post and returns the Post")
  @DeleteMapping("posts/unlike/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post unlikePost(@RequestBody User user, @PathVariable Long postId);

  @Operation(summary = "Returns all Likes for Post", description = "Returns a list of Likes for a Post ID")
  @GetMapping("posts/likes/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<Like> getLikesForPost(@PathVariable Long postId);
}