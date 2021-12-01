package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.User;
import com.promineotech.twitter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultUserController implements UserController {

  @Autowired
  private UserService userService;

  @Override
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @Override
  public User getUser(Long userId) {
    return userService.getUserById(userId);
  }

  @Override
  public User createUser(User user) {
    return userService.createUser(user);
  }

  @Override
  public User updateUser(User user, Long userId) {
    return userService.updateUser(user, userId);
  }

  @Override
  public void deleteUser(Long userId) {
    userService.deleteUser(userId);
  }
  
}
