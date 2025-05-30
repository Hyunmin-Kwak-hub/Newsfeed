package com.example.newsfeed.comment.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long articleId;
    private Long userId;
    private String content;
}
