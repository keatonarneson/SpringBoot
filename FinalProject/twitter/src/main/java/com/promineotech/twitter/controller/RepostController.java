package com.promineotech.twitter.controller;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface RepostController {
  
  @PostMapping("posts/repost/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post addRepost(@RequestBody User authUser, @PathVariable Long postId);

  @DeleteMapping("posts/reposts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  void removeRepost(@RequestBody User authUser, @PathVariable Long postId);
}
