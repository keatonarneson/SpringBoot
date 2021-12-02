package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.entity.Like;
import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

public interface LikeService {

  Post likePost(Long postId, User likedUser);

  Post unlikePost(Long postId, User likedUser);

  List<Like> getLikesForPost(Long postId);
}
