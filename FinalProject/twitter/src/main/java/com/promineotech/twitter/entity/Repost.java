package com.promineotech.twitter.entity;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Repost {
  private Long repostId;
  private Post post;
  private User user;
  private Date repostedOn;
}