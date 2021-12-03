package com.promineotech.twitter.entity;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Follower {
  private Long followerId;
  private User followerUser;
  private User followingUser;
  private Date followedOn;
}