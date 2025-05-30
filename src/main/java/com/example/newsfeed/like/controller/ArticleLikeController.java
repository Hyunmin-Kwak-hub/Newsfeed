package com.example.newsfeed.like.controller;

import com.example.newsfeed.like.controller.dto.ArticleLikeReqDto;
import com.example.newsfeed.like.controller.dto.ArticleLikeResDto;
import com.example.newsfeed.like.domain.entity.ArticleLike;
import com.example.newsfeed.like.service.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/likes")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    //  게시글 좋아요 추가

    @PostMapping
    public ResponseEntity<ArticleLikeResDto> likeArticle(@RequestBody ArticleLikeReqDto dto) {
        ArticleLike like = articleLikeService.likeArticle(dto.getUserId(), dto.getArticleId());
        return ResponseEntity.status(201).body(new ArticleLikeResDto(like));
    }


    //  게시글 전체 좋아요 조회
    @GetMapping
    public ResponseEntity<Long> countArticleLikes(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleLikeService.countLikes(articleId));
    }

    // 게시글 좋아요 삭제

    @DeleteMapping
    public ResponseEntity<Void> unlikeArticle(@RequestBody ArticleLikeReqDto dto) {
        articleLikeService.unlikeArticle(dto.getUserId(), dto.getArticleId());
        return ResponseEntity.noContent().build();
    }
    }
