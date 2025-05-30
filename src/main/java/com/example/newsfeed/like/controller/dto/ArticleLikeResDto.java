package com.example.newsfeed.like.controller.dto;

import com.example.newsfeed.like.domain.entity.ArticleLike;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ArticleLikeResDto {
    private final Long id;
    private final Long userId;
    private final Long articleId;
    private final LocalDateTime createdDateTime;

    public ArticleLikeResDto(ArticleLike like) {
        this.id = like.getId();
        this.userId = like.getUserId();
        this.articleId = like.getArticleId();
        this.createdDateTime = like.getCreatedDateTime();
    }
}