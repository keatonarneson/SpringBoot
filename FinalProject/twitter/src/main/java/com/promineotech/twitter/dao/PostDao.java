package com.promineotech.twitter.dao;

import java.util.List;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.entity.User;

public interface PostDao {
  List<Post> getAllPostsForUser(User user);

  Post getPostById(Long postId);

  Post savePost(Post post);

  void deletePost(Long postId);

  User fetchUser(Long userId);

  
}