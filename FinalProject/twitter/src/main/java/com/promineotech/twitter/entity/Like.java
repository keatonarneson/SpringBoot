package com.promineotech.twitter.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Like {
  private Long likeId;
  private Post post;
  private User user;
  private String likedOn;
}



