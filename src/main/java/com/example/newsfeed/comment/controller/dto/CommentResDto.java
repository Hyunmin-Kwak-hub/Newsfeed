package com.example.newsfeed.comment.controller.dto;

import com.example.newsfeed.comment.domain.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {
    private Long id;
    private Long articleId;
    private Long userId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.articleId = comment.getArticleId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();
    }
}