package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.dao.FollowDao;
import com.promineotech.twitter.entity.Follower;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultFollowService implements FollowService {

  @Autowired
  private FollowDao followDao;

  @Transactional
  @Override
  public Follower followUser(Long userId, User authUser) {

    return followDao.followUser(userId, authUser);
  }

  @Transactional
  @Override
  public void unfollowUser(Long userId, User authUser) {

    followDao.unfollowUser(userId, authUser);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getFollowersForUser(Long userId) {

    return followDao.getFollowersForUser(userId);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getFollowingForUser(Long userId) {

    return followDao.getFollowingForUser(userId);
  }
  
}
