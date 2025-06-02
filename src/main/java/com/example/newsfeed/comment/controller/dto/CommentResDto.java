package com.example.newsfeed.comment.controller.dto;

import com.example.newsfeed.comment.domain.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 응답 DTO
@Getter
public class CommentResDto {

    // 댓글 ID (PK)
    private Long id;

    // 댓글 작성자 ID
    private Long userId;

    // 댓글 내용
    private String content;

    // 댓글 생성 시각 (UTC)
    private LocalDateTime createdDateTime;

    // 댓글 마지막 수정 시각 (UTC)
    private LocalDateTime updatedDateTime;

    //Comment Entity를 기반으로 DTO에 값을 매핑한다.
    public CommentResDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();    // 실제 연관관계에서 user 객체로부터 ID 추출
        this.content = comment.getContent();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();
    }
}