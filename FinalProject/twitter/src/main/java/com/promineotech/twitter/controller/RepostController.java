package com.promineotech.twitter.controller;

import com.promineotech.twitter.entity.Post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;

public interface RepostController {
  @Operation(summary = "Create a Repost", description = "Create a Repost with a given Post ID")
  @PostMapping("posts/repost/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post addRepost(@RequestParam Long authUserId, @PathVariable Long postId);

  @Operation(summary = "Delete a Repost", description = "Delete a Repost with a given Post ID")
  @DeleteMapping("posts/reposts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  void removeRepost(@RequestParam Long authUserId, @PathVariable Long postId);
}
