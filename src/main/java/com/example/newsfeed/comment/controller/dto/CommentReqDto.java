package com.example.newsfeed.comment.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 댓글 요청 DTO
@Getter
@Setter
@NoArgsConstructor
public class CommentReqDto {

    // 댓글 작성자 ID (user 테이블의 PK)
    private Long userId;

    // 댓글 내용
    private String content;
}