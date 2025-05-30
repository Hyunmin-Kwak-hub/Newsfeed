package com.example.newsfeed.article.controller.dto;

import com.example.newsfeed.article.domain.entity.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String content;
    private final String imgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedDateTime;

    public ArticleResDto(Article article) {
        this.id = article.getId();
        this.username = article.getUser().getUsername();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDateTime = article.getCreatedDateTime();
        this.updatedDateTime = article.getUpdatedDateTime();
        this.imgUrl = article.getImgUrl();
    }
}