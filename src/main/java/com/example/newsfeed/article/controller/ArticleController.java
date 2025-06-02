package com.example.newsfeed.article.controller;

import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.service.ArticleService;
import com.example.newsfeed.global.dto.AuthUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ArticleResDto createArticle(@Valid @RequestBody ArticleReqDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((AuthUserDto) authentication.getPrincipal()).getId();

        return articleService.createArticle(userId, requestDto);
    }

    @GetMapping
    public Page<ArticleResDto> getAllArticles(@RequestParam(defaultValue = "0") int page) {
        return articleService.getAllArticles(page);
    }

    @GetMapping("/{id}")
    public ArticleResDto getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @GetMapping("/search/{userId}")
    public Page<ArticleResDto> getArticlesByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page) {
        return articleService.getArticlesByUser(userId, page);
    }

    @GetMapping("/search")
    public Page<ArticleResDto> getArticlesByDateBetween(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate")LocalDate endDate, @RequestParam(defaultValue = "0")int page) {
        return articleService.getArticlesByDateBetween(startDate, endDate, page);
    }

    @PutMapping("/{id}")
    public ArticleResDto updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleReqDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((AuthUserDto) authentication.getPrincipal()).getId();

        return articleService.updateArticle(id, userId, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((AuthUserDto) authentication.getPrincipal()).getId();

        articleService.deleteArticle(id, userId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}