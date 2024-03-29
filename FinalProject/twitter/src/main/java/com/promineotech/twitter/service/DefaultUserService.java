package com.promineotech.twitter.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.promineotech.twitter.dao.UserDao;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserService implements UserService {

  @Autowired
  private UserDao userDao;

  @Transactional(readOnly = true)
  @Override
  public List<User> getAllUsers() {
    List<User> users = userDao.getAllUsers();

    return users;
  }

  @Transactional(readOnly = true)
  @Override
  public User getUserById(Long userId) {
    checkIfUserExists(userId);
    
    User fetchedUser = userDao.getUserById(userId);

    return fetchedUser;
  }

  @Transactional
  @Override
  public User createUser(User user) {
    return userDao.saveUser(user);
  }

  @Transactional
  @Override
  public User updateUser(User user, Long userId) {
    checkIfUserExists(userId);

    return userDao.updateUser(user, userId);
  }

  @Transactional
  @Override
  public void deleteUser(Long userId) {
    checkIfUserExists(userId);

    userDao.deleteUser(userId);
  }


  // ---------------------------------------------------------------------------------------
  public void checkIfUserExists(Long userId) {
    User fetchedUser = userDao.getUserById(userId);

    if (fetchedUser == null) {
      String msg = String.format("No User with User ID = %d", userId);

      throw new NoSuchElementException(msg);
    }
  }
}




