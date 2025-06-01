package com.example.newsfeed.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResDto {

    private final int status;
    private final String error;
    private final String message;
    private final String errorDetail;
    private final String timestamp;

}