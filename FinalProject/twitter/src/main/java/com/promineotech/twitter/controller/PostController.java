package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/api/v1")
public interface PostController {

  @Operation(summary = "Get all Posts for User", description = "Returns list of Posts for User", 
  responses = {
      @ApiResponse(responseCode = "200", description = "Returns list of Posts for User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Posts were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      })
  @GetMapping("posts")
  @ResponseStatus(code = HttpStatus.OK)
  List<Post> getAllPostsForUser(@RequestParam Long userId);

  @Operation(summary = "Get all Posts and Reposts for User", description = "Returns list of Posts and Reposts for User", 
  responses = {
      @ApiResponse(responseCode = "200", description = "Returns list of Posts and Reposts for User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Posts or Reposts were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      })
  @GetMapping("posts-reposts")
  @ResponseStatus(code = HttpStatus.OK)
  List<Post> getAllPostsAndRepostsForUser(@RequestParam Long userId);

  @Operation(summary = "Get all Posts, Reposts, and Replies for User", description = "Returns list of Posts, Reposts, and Replies for User", 
  responses = {
      @ApiResponse(responseCode = "200", description = "Returns list of Posts, Reposts, and Replies for User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Posts, Reposts, or Replies were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      })
  @GetMapping("posts-reposts-replies")
  @ResponseStatus(code = HttpStatus.OK)
  List<Post> getAllPostsAndRepostsAndRepliesForUser(@RequestParam Long userId);

  @Operation(summary = "Get Post", description = "Returns Post by Post ID", 
  responses = {
      @ApiResponse(responseCode = "200", description = "Returns Post by Post ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Post was found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @GetMapping("posts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post getPost(@PathVariable Long postId);

  @Operation(summary = "Create a Post", description = "Creates a Post and returns that Post",
      responses = {
      @ApiResponse(responseCode = "201", description = "The Post was created and returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Post was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    })
  @PostMapping("posts")
  @ResponseStatus(code = HttpStatus.CREATED)
  Post createPost(@RequestBody Post post);

  @Operation(summary = "Delete a Post", description = "Deletes a Post by Post ID and returns nothing",
      responses = {
      @ApiResponse(responseCode = "204", description = "The specified Post has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Post was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @DeleteMapping("posts/{postId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  void deletePost(@PathVariable Long postId);
}