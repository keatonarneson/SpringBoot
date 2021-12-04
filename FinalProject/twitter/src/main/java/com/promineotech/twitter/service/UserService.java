package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.entity.User;

public interface UserService {
  List<User> getAllUsers();

  User getUserById(Long userId);

  User createUser(User user);

  User updateUser(User user, Long userId);

  void deleteUser(Long userId);

  void checkIfUserExists(Long userId);
}
