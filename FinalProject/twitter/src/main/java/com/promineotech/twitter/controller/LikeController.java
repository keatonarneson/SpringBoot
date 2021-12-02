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

@RequestMapping("/api/v1")
public interface LikeController {

  @PostMapping("posts/{postId}/like")
  @ResponseStatus(code = HttpStatus.OK)
  Post likePost(@RequestBody User user, @PathVariable Long postId);

  @DeleteMapping("posts/{postId}/unlike")
  @ResponseStatus(code = HttpStatus.OK)
  Post unlikePost(@RequestBody User user, @PathVariable Long postId);

  @GetMapping("posts/{postId}/likes")
  @ResponseStatus(code = HttpStatus.OK)
  List<Like> getLikesForPost(@PathVariable Long postId);
}