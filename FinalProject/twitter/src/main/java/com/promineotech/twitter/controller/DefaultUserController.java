package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultUserController implements UserController {

  @Autowired
  private UserService userService;

  @Override
  public List<User> getAllUsers() {
    log.info("user list requested (user controller)");

    return userService.getAllUsers();
  }

  @Override
  public User getUser(Long userId) {
    
    return userService.getUserById(userId);
  }

  @Override
  public User createUser(User user) {
    log.info("{}", user);
    return userService.createUser(user);
  }

  @Override
  public User updateUser(User user) {
    return userService.updateUser(user);
  }
  
}
