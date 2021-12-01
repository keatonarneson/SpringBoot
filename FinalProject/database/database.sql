CREATE DATABASE IF NOT EXISTS twitter;

USE twitter;

DROP TABLE IF EXISTS reposts;
DROP TABLE IF EXISTS followers;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  user_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  bio VARCHAR(255),
  location VARCHAR(255),
  website VARCHAR(255),
  birthdate DATE,
  avatar_url VARCHAR(255),
  cover_photo_url VARCHAR(255),
  followers_count INT DEFAULT 0,
  following_count INT DEFAULT 0,
  registered_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id)
);

CREATE TABLE posts(
  post_id INT NOT NULL AUTO_INCREMENT,
  content VARCHAR(280) DEFAULT '',
  posted_by INT NOT NULL,
  posted_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  like_count INTEGER DEFAULT 0,
  reply_count INTEGER DEFAULT 0,
  parent_id INTEGER,
  PRIMARY KEY (post_id),
  FOREIGN KEY (posted_by) REFERENCES users(user_id),
  FOREIGN KEY (parent_id) REFERENCES posts(post_id)
);

CREATE TABLE likes(
  like_id INT NOT NULL AUTO_INCREMENT,
  post_id INT NOT NULL,
  user_id INT NOT NULL,
  liked_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (like_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE followers(
  follower_id INT NOT NULL AUTO_INCREMENT,
  follower_user INT NOT NULL,
  following_user INT NOT NULL,
  followed_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(follower_id),
  FOREIGN KEY(follower_user) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY(following_user) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE reposts(
  repost_id INT NOT NULL AUTO_INCREMENT,
  post_id INT NOT NULL,
  user_id INT NOT NULL,
  reposted_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (repost_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);






