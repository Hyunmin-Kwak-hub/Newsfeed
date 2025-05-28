package com.example.newsfeed.article.controller;

import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ArticleResDto create(@Valid @RequestBody ArticleReqDto requestDto, HttpServletRequest request) {
        return articleService.create(requestDto, request);
    }

    @GetMapping
    public List<ArticleResDto> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public ArticleResDto getOne(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @PutMapping("/{id}")
    public ArticleResDto update(@PathVariable Long id, @RequestBody ArticleReqDto requestDto) {
        return articleService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}