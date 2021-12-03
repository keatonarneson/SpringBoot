package com.promineotech.twitter.service;

import com.promineotech.twitter.entity.Post;

public interface RepostService {

  Post addRepost(Long postId, Long authUserId);

  void removeRepost(Long postId, Long authUserId);
}
