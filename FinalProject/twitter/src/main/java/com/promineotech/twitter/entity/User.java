package com.promineotech.twitter.entity;


import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Long userId;
  private String username;
  private String email;
  private String password;
  private String name;
  private String bio;
  private String location;
  private String website;
  private Date birthdate;
  private String avatarURL;
  private String coverPhotoURL;
  private int followersCount;
  private int followingCount;
  private Date registeredOn;


  @JsonIgnore
  public Long getUserId() {
    return userId;
  }

  @JsonIgnore
  public Date getRegisteredOn() {
    return registeredOn;
  }
}


