package com.example.newsfeed.article.controller;

import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ArticleResDto createArticle(@Valid @RequestBody ArticleReqDto requestDto, HttpServletRequest request) {
        return articleService.createArticle(requestDto, request);
    }

    @GetMapping
    public Page<ArticleResDto> getAllArticles(@RequestParam(defaultValue = "0") int page) {
        return articleService.getAllArticles(page);
    }

    @GetMapping("/{id}")
    public ArticleResDto getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @GetMapping("/articles/user")
    public Page<ArticleResDto> getArticlesByUser(@RequestParam Long userId, @RequestParam(defaultValue = "0") int page) {
        return articleService.getArticlesByUser(userId, page);
    }

    @PutMapping("/{id}")
    public ArticleResDto updateArticle(@PathVariable Long id, @RequestBody ArticleReqDto requestDto, @RequestParam Long userId) {
        return articleService.updateArticle(id, requestDto, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.deleteArticle(id, userId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}