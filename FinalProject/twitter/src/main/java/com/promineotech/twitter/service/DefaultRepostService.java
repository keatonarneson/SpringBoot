package com.promineotech.twitter.service;

import com.promineotech.twitter.dao.RepostDao;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultRepostService implements RepostService {

  @Autowired
  private RepostDao repostDao;

  @Transactional
  @Override
  public Post addRepost(Long postId, Long authUserId) {

    return repostDao.addRepost(postId, authUserId);
  }

  @Transactional
  @Override
  public void removeRepost(Long postId, Long authUserId) {

    repostDao.removeRepost(postId, authUserId);
  } 
}
