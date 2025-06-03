package com.example.newsfeed.like.controller;

import com.example.newsfeed.like.controller.dto.CommentLikeReqDto;
import com.example.newsfeed.like.controller.dto.CommentLikeResDto;
import com.example.newsfeed.like.domain.entity.CommentLike;
import com.example.newsfeed.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/articles/{article_id}/comments/{comment_id}/likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 좋아요 추가
    @PostMapping
    public ResponseEntity<CommentLikeResDto> likeComment(
            @PathVariable("comment_id") Long commentId,
            @RequestBody CommentLikeReqDto dto) {

        CommentLike like = commentLikeService.likeComment(dto.getUserId(), commentId);
        return ResponseEntity.ok(new CommentLikeResDto(like));
    }

    // 댓글 좋아요 삭제
    @DeleteMapping
    public ResponseEntity<Void> unlikeComment(
            @PathVariable("comment_id") Long commentId,
            @RequestBody CommentLikeReqDto dto) {

        commentLikeService.unlikeComment(dto.getUserId(), commentId);
        return ResponseEntity.noContent().build();
    }

    // 댓글 좋아요 조회
    @GetMapping
    public ResponseEntity<Long> countCommentLikes(
            @PathVariable("comment_id") Long commentId) {

        return ResponseEntity.ok(commentLikeService.countLikes(commentId));
    }
}