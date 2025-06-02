package com.example.newsfeed.like.controller.dto;


import lombok.Getter;


@Getter

public class ArticleLikeReqDto {
    private Long userId;
    private Long articleId;
    private Long commentId;
}