package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/twitter")
public interface UserController {
  @Operation(
    summary = "Returns all Users",
    description = "Returns all Users",
      responses = {
      @ApiResponse(responseCode = "200", description = "A list of Users is returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "No Users were found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    }
  )
  @GetMapping("users")
  @ResponseStatus(code = HttpStatus.OK)
  List<User> getAllUsers();

  @Operation(
    summary = "Returns a User",
    description = "Returns a User by a given User ID",
      responses = {
      @ApiResponse(responseCode = "200", description = "The specified user is returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "The request paramaters are invalid", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "404", description = "The User was not found", content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "500", description = "An unplanned error occured", content = @Content(mediaType = "application/json"))  
    },
      parameters = {
        @Parameter(name = "userId", allowEmptyValue = false, description = "The User ID")
      }
    
  )
  @GetMapping("users/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  User getUser(@PathVariable Long userId);

  @PostMapping("users")
  @ResponseStatus(code = HttpStatus.CREATED)
  User createUser(@RequestBody User user);

  @PutMapping("users")
  @ResponseStatus(code = HttpStatus.OK)
  User updateUser(@RequestBody User user);
}
