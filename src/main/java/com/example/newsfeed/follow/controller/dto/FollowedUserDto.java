package com.example.newsfeed.follow.controller.dto;


import lombok.Getter;

// 팔로우 목록 반환을 위한 DTO
@Getter
public class FollowedUserDto {

    private String username;
    private String email;

    public FollowedUserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
