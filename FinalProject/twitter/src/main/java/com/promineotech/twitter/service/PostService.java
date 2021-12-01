package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.entity.Post;

public interface PostService {
  List<Post> getAllPostsForUser(Long userId);

  Post getPostById(Long postId);

  Post createPost(Post post);

  void deletePost(Long postId);
}
