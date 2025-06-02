package com.example.newsfeed.like.domain.repository;

import com.example.newsfeed.like.domain.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);
    void deleteByUserIdAndArticleId(Long userId, Long articleId);
    long countByArticleId(Long articleId);

}