package com.example.newsfeed.like.service;

import com.example.newsfeed.comment.domain.repository.CommentRepository;
import com.example.newsfeed.like.domain.entity.CommentLike;
import com.example.newsfeed.like.domain.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentLike likeComment(Long userId, Long commentId) {
        Long authorId = commentRepository.findAuthorIdByCommentId(commentId);
        if (authorId != null && authorId.equals(userId)) {
            throw new IllegalArgumentException("본인 댓글에는 좋아요를 누를 수 없습니다.");
        }
        commentLikeRepository.findByUserIdAndCommentId(userId, commentId)
                .ifPresent(like -> {
                    throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
                });
        return commentLikeRepository.save(new CommentLike(userId, commentId));
    }

    @Transactional
    public void unlikeComment(Long userId, Long commentId) {
        commentLikeRepository.deleteByUserIdAndCommentId(userId, commentId);
    }

    @Transactional(readOnly = true)
    public long countLikes(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}