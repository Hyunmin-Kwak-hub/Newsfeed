package com.example.newsfeed.article.domain.repository;

import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByUserId(Long userId, Pageable pageable);

    Page<Article> findByCreatedDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    @Query("select a.user.id from Article a where a.id = :articleId")
    Long findAuthorIdByArticleId(@Param("articleId") Long articleId);

    // 팔로우 기능 중 팔로워 게시글 전체 조회 기능에 사용하는 코드
    List<Article> findByUserInOrderByUpdatedDateTimeDesc(List<User> users);
}
