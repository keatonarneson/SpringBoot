package com.promineotech.twitter.controller;

import java.util.List;

import com.promineotech.twitter.entity.Post;
import com.promineotech.twitter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultPostController implements PostController {

  @Autowired
  private PostService postService;

  @Override
  public List<Post> getAllPostsForUser(Long userId) {
    return postService.getAllPostsForUser(userId);
  }

  @Override
  public List<Post> getAllPostsAndRepostsForUser(Long userId) {

    return postService.getAllPostsAndRepostsForUser(userId);
  }

  @Override
  public List<Post> getAllPostsAndRepostsAndRepliesForUser(Long userId) {

    return postService.getAllPostsAndRepostsAndRepliesForUser(userId);
  }

  @Override
  public Post getPost(Long postId) {
    return postService.getPostById(postId);
  }

  @Override
  public Post createPost(Post post) {

    return postService.createPost(post);
  }

  @Override
  public void deletePost(Long postId) {
    postService.deletePost(postId);
  }
}
