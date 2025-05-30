package com.example.newsfeed.comment.service;

import com.example.newsfeed.comment.controller.dto.CommentRequestDto;
import com.example.newsfeed.comment.controller.dto.CommentResponseDto;
import com.example.newsfeed.comment.domain.entity.Comment;
import com.example.newsfeed.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 생성
    public CommentResponseDto createComment(Long articleId, CommentRequestDto requestDto) {
        Comment comment = Comment.builder()
                .userId(requestDto.getUserId())
                .articleId(articleId)
                .content(requestDto.getContent())
                .build();

        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 특정 게시글 전체 댓글 조회
    public List<CommentResponseDto> getCommentsByArticle(Long id) {
        return commentRepository.findByArticleId(id).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 단건 조회
    public CommentResponseDto getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(CommentResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }
}
