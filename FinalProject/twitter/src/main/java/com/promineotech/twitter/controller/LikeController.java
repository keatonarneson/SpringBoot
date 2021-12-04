package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Like;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/api/v1")
public interface LikeController {

  @Operation(summary = "Like a Post", description = "Like a Post and returns the Post",
  responses = {
      @ApiResponse(responseCode = "201", description = "The Like was created and the Post was returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Like.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Post was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID"),
        @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @PostMapping("posts/like/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post likePost(@RequestParam Long userId, @PathVariable Long postId);

  @Operation(summary = "Unlike a Post", description = "Unlike a Post and returns the Post",
  responses = {
      @ApiResponse(responseCode = "204", description = "The Like was deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Like.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Like was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID"),
        @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @DeleteMapping("posts/unlike/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post unlikePost(@RequestParam Long userId, @PathVariable Long postId);

  @Operation(summary = "Returns all Likes for Post", description = "Returns a list of Likes for a Post ID",
   responses = {
      @ApiResponse(responseCode = "200", description = "A list of Likes for Post is returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Like.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Likes were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @GetMapping("posts/likes/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<Like> getLikesForPost(@PathVariable Long postId);
}