package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.controller.dto.CommentReqDto;
import com.example.newsfeed.comment.controller.dto.CommentResDto;
import com.example.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 댓글 관련 HTTP 요청을 처리하는 REST 컨트롤러
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/comments")
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

    // 댓글 수정
    @PutMapping("/{id}")
        public ResponseEntity<CommentResDto> updateComment(
                @PathVariable Long id,
                @RequestBody CommentReqDto dto
    ) {
        commentService.updateComment(id, dto);
        return ResponseEntity.ok().build(); // 리턴 값
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
