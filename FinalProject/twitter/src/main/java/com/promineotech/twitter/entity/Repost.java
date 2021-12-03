package com.promineotech.twitter.entity;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Repost {
  private Long respostId;
  private Post post;
  private User user;
  private Date repostedOn;
}