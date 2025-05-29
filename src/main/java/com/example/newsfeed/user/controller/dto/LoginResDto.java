package com.example.newsfeed.user.controller.dto;

import lombok.Getter;

@Getter
public class LoginResDto {

    private final String token;
    private final String userName;

    public LoginResDto(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

}