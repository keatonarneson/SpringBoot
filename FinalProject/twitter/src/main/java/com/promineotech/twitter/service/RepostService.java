package com.promineotech.twitter.service;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

public interface RepostService {

  Post addRepost(Long postId, User authUser);

  void removeRepost(Long postId, User authUser);
}
