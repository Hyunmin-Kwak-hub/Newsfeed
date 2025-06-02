package com.example.newsfeed.follow.controller.dto;


import lombok.Getter;

@Getter
public class FollowedUserDto {

    private String username;
    private String email;

    public FollowedUserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
