package com.example.newsfeed.global.common;

public interface ApiUrls {
    String[] USER = {"/api/users", "/api/users/login", "/api/users/*", "/api/users/*/password"};
    String[] ARTICLE = {"/api/articles", "/api/*/articles", "/api/articles/*"};
    String[] COMMENT = {"/api/articles/*/comments", "/api/articles/*/comments/*"};
    String[] FOLLOW = {"/api/*/follows", "/api/*/follows/articles", "/api/*/unfollows/*"};
    String[] LIKE = {"/api/articles/*/likes", "/api/articles/*/comments/*/likes"};
}