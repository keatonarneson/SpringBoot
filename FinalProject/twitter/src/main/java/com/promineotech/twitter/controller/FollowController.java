package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;

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
public interface FollowController {

  @Operation(summary = "Follow a User", description = "Follow a User by User ID",
      responses = {
      @ApiResponse(responseCode = "201", description = "The Auth User followed the User and returned the Follower", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Follower.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Follower was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))
    },
     parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID"),
        @Parameter(name = "authUserId", allowEmptyValue = false, description = "The Auth User ID")
      }
    )
  @PostMapping("users/follow/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  Follower followUser(@RequestParam Long authUserId, @PathVariable Long userId);

  @Operation(summary = "Unfollow a User", description = "Unfollow a User by User ID",
      responses = {
      @ApiResponse(responseCode = "204", description = "The User has been unfollowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Follower.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The Follower was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID"),
        @Parameter(name = "authUserId", allowEmptyValue = false, description = "The Auth User ID")
      })
  @DeleteMapping("users/unfollow/{userId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  void unfollowUser(@RequestParam Long authUserId, @PathVariable Long userId);

  @Operation(summary = "Get all Followers for User", description = "Returns a list of Users who are Followers of User ID",
      responses = {
      @ApiResponse(responseCode = "200", description = "A list of Followers is returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Follower.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Followers were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      })
  @GetMapping("users/followers/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowersForUser(@PathVariable Long userId);

  @Operation(summary = "Get all Followings for User", description = "Returns a list of Users who are Following of User ID",
      responses = {
      @ApiResponse(responseCode = "200", description = "A list of Followings is returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Follower.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Followings were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      })
  @GetMapping("users/following/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getFollowingForUser(@PathVariable Long userId);
  
}
