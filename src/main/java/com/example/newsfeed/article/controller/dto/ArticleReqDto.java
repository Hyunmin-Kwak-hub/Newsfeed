package com.example.newsfeed.article.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ArticleReqDto {
    @Size(min = 1, message = "제목은 한 글자 이상이어야 합니다.")
    private String title;

    private String content;

    private String imgUrl;
}