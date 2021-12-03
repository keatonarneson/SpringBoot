package com.promineotech.twitter.dao;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

public interface RepostDao {

  Post addRepost(Long postId, Long authUserId);

  void removeRepost(Long postId, Long authUserId);
  
}
