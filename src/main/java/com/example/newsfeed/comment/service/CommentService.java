package com.example.newsfeed.comment.service;

import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.article.domain.repository.ArticleRepository;
import com.example.newsfeed.comment.controller.dto.CommentReqDto;
import com.example.newsfeed.comment.controller.dto.CommentResDto;
import com.example.newsfeed.comment.domain.entity.Comment;
import com.example.newsfeed.comment.domain.repository.CommentRepository;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    // 댓글 생성 - 연관관계로 사용자, 게시글 객체 연결
    public CommentResDto createComment(Long articleId, CommentReqDto requestDto) {
        // 유저 찾기
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 게시글 찾기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 연관관계 기반 댓글 생성
        Comment comment = Comment.builder()
                .user(user)                         // 사용자 연관관계
                .article(article)                   // 게시글 연관관계
                .content(requestDto.getContent())   // 댓글 내용
                .build();

        return new CommentResDto(commentRepository.save(comment));
    }

    // 특정 게시글 전체 댓글 조회

    public List<CommentResDto> getCommentsByArticle(Long id) {
        return commentRepository.findByArticleId(id).stream()
                    .map(CommentResDto::new)    // 각 Comment를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 댓글 단건 조회
    // 댓글이 존재하지 않을 경우 예외 발생
    public CommentResDto getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(CommentResDto::new)    // Optional에서 Comment 꺼내서 DTO로 변환
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

    // 댓글 수정
    // 댓글이 존재하지 않을 경우 예외 발생
    public CommentResDto updateComment(Long id, CommentReqDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 댓글 내용 수정
        comment.updateContent(dto.getContent());

        // 수정된 댓글 저장 후 DTO로 반환
        return new CommentResDto(commentRepository.save(comment));
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
