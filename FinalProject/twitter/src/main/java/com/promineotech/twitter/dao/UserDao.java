package com.promineotech.twitter.dao;

import java.util.List;

import com.promineotech.twitter.entity.User;

public interface UserDao {
  List<User> getAllUsers();

  User getUserById(Long userId);

  User saveUser(User user);

  User updateUser(User user);
}
