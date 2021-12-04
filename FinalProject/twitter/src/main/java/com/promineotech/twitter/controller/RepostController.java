package com.promineotech.twitter.controller;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.Repost;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface RepostController {
  @Operation(summary = "Create a Repost", description = "Create a Repost with a given Post ID and returns that Post",
  responses = {
      @ApiResponse(responseCode = "201", description = "The Repost was created and returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Repost.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Repost was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
    parameters = {
          @Parameter(name = "authUserId", allowEmptyValue = false, description = "The User ID"),
          @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @PostMapping("posts/repost/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  Post addRepost(@RequestParam Long authUserId, @PathVariable Long postId);

  @Operation(summary = "Delete a Repost", description = "Delete a Repost with a given Post ID",
  responses = {
      @ApiResponse(responseCode = "204", description = "The specified Repost has been deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Repost.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Repost was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
    parameters = {
          @Parameter(name = "authUserId", allowEmptyValue = false, description = "The User ID"),
          @Parameter(name = "postId", allowEmptyValue = false, description = "The Post ID")
      })
  @DeleteMapping("posts/reposts/{postId}")
  @ResponseStatus(code = HttpStatus.OK)
  void removeRepost(@RequestParam Long authUserId, @PathVariable Long postId);
}
