package com.example.newsfeed.global.dto;

import lombok.Getter;

@Getter
public class AuthUserDto {

    private final Long id;
    private final String username;

    public AuthUserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}