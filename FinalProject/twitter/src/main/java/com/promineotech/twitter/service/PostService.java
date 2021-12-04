package com.promineotech.twitter.service;

import java.util.List;

import com.promineotech.twitter.entity.Post;

public interface PostService {
  List<Post> getAllPostsForUser(Long userId);

  List<Post> getAllPostsAndRepostsForUser(Long userId);

  List<Post> getAllPostsAndRepostsAndRepliesForUser(Long userId);

  Post getPostById(Long postId);

  Post createPost(Post post);

  void deletePost(Long postId);

  void checkIfPostExists(Long postId);
}
