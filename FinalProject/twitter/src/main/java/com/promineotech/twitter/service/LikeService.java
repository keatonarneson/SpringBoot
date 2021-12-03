package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;

public interface LikeService {

  Post likePost(Long postId, Long likedUserId);

  Post unlikePost(Long postId, Long likedUserId);

  List<Like> getLikesForPost(Long postId);
}
