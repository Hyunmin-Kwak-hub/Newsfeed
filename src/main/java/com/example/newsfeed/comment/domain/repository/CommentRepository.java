package com.example.newsfeed.comment.domain.repository;

import com.example.newsfeed.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
// 댓글 Entity에 대한 JPA 레포지토리 인터페이스
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 해당하는 모든 댓글을 조회한다.
    List<Comment> findByArticleId(Long articleId);
    @Query("select c.user.id from Comment c where c.id = :commentId")
    Long findAuthorIdByCommentId(@Param("commentId") Long commentId);
}
