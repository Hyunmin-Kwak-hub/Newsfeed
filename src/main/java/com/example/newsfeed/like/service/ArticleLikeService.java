package com.example.newsfeed.like.service;


import com.example.newsfeed.article.domain.repository.ArticleRepository;
import com.example.newsfeed.like.domain.entity.ArticleLike;
import com.example.newsfeed.like.domain.repository.ArticleLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;



    @Transactional
    public ArticleLike likeArticle(Long userId, Long articleId) {
        Long authorId = articleRepository.findAuthorIdByArticleId(articleId);
        if (authorId != null && authorId.equals(userId)) {
            throw new IllegalArgumentException("본인 글에는 좋아요를 누를 수 없습니다.");
        }
        articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
                .ifPresent(like -> {
                    throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
                });
        return articleLikeRepository.save(new ArticleLike(userId, articleId));
    }

    @Transactional
    public long countLikes(Long articleId) {
        return articleLikeRepository.countByArticleId(articleId);
    }


    @Transactional
    public void unlikeArticle(Long userId, Long articleId) {
        articleLikeRepository.deleteByUserIdAndArticleId(userId, articleId);
    }




}