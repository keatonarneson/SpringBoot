package com.promineotech.twitter.entity;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
  private Long postId;
  private String content;
  private User postedBy;
  private Date postedOn;
  private int likeCount;
  private int replyCount;
  private Post parentPost;
}