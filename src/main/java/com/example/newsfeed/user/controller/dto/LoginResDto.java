package com.example.newsfeed.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDto {

    private final String token;
    private final String userName;

}