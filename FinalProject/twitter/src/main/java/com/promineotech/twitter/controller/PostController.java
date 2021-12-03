package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/api/v1")
public interface PostController {

  @Operation(summary = "Get all Posts for User", description = "Returns list of Posts for User")
  @GetMapping("posts")
  @ResponseStatus(code = HttpStatus.OK)
  List<Post> getAllPostsForUser(@RequestParam Long userId);

  @Operation(summary = "Get Post", description = "Returns Post by Post ID")
  @GetMapping("posts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post getPost(@PathVariable Long postId);

  @Operation(summary = "Create a Post", description = "Creates a Post and returns that Post")
  @PostMapping("posts")
  @ResponseStatus(code = HttpStatus.CREATED)
  Post createPost(@RequestBody Post post);

  @Operation(summary = "Delete a Post", description = "Deletes a Post by Post ID and returns nothing")
  @DeleteMapping("posts/{postId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  void deletePost(@PathVariable Long postId);
}