package com.promineotech.twitter.controller;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;

public interface RepostController {
  @Operation(summary = "Create a Repost", description = "Create a Repost with a given Post ID")
  @PostMapping("posts/repost/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post addRepost(@RequestBody User authUser, @PathVariable Long postId);

  @Operation(summary = "Delete a Repost", description = "Delete a Repost with a given Post ID")
  @DeleteMapping("posts/reposts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  void removeRepost(@RequestBody User authUser, @PathVariable Long postId);
}
