package com.example.newsfeed.like.service;

import com.example.newsfeed.like.domain.entity.CommentLike;
import com.example.newsfeed.like.domain.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentLike likeComment(Long userId, Long commentId) {
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