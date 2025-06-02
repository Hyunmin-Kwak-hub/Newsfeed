package com.example.newsfeed.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserDto {

    private final Long id;
    private final String username;

}