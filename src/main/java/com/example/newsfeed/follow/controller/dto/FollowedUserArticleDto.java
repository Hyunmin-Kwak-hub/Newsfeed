package com.example.newsfeed.follow.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 팔로우 게시글 반환을 위한 DTO
@Getter
@AllArgsConstructor
public class FollowedUserArticleDto {
    private String username;
    private String email;
    private String title;
    private String content;
    private String imgUrl;
    private LocalDateTime updatedAt;

}
