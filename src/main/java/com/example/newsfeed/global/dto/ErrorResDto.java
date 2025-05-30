package com.example.newsfeed.global.dto;

import lombok.Getter;

@Getter
public class ErrorResDto {

    private final int status;
    private final String error;
    private final String message;
    private final String timestamp;

    public ErrorResDto(int status, String error, String message, String timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

}