package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.controller.dto.CommentReqDto;
import com.example.newsfeed.comment.controller.dto.CommentResDto;
import com.example.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles／{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResDto> createComment(
            @PathVariable Long articleId,
            @RequestBody CommentReqDto dto
    ) {
        return new ResponseEntity<>(commentService.createComment(articleId, dto), HttpStatus.CREATED);
    }

    // 댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentResDto>> getComments(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.getCommentsByArticle(articleId));
    }

    // 댓글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResDto> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

}
