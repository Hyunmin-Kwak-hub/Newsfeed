package com.example.newsfeed.like.controller.dto;

import com.example.newsfeed.like.domain.entity.CommentLike;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentLikeResDto {
    private final Long id;
    private final Long userId;
    private final Long commentId;
    private final LocalDateTime createdDateTime;

    public CommentLikeResDto(CommentLike like) {
        this.id = like.getId();
        this.userId = like.getUserId();
        this.commentId = like.getCommentId();
        this.createdDateTime = like.getCreatedDateTime();
    }
}