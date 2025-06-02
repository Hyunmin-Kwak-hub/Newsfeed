package com.example.newsfeed.article.domain.repository;

import com.example.newsfeed.article.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByUserId(Long userId, Pageable pageable);

    Page<Article> findByCreatedDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    @Query("select a.user.id from Article a where a.id = :articleId")
    Long findAuthorIdByArticleId(@Param("articleId") Long articleId);
}
