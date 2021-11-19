package com.promineotech.twitter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
  private Long postId;
  private String content;
  private User postedBy;
  private String postedOn;
  private int likeCount;
  private int replyCount;
  private Post parentPost;
}