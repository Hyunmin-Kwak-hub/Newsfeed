package com.example.newsfeed.article.service;

import com.example.newsfeed.User.domain.entity.User;
import com.example.newsfeed.User.domain.repository.UserRepository;
import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.article.domain.repository.ArticleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleResDto create(ArticleReqDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Article article = new Article(user, requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl());
        return new ArticleResDto(articleRepository.save(article));
    }

    public List<ArticleResDto> getAll() {
        return articleRepository.findAll().stream()
                .map(ArticleResDto::new)
                .toList();
    }

    public ArticleResDto getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        return new ArticleResDto(article);
    }

    public ArticleResDto update(Long id, ArticleReqDto requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        article.setTitle(requestDto.getTitle());
        article.setContent(requestDto.getContent());

        return new ArticleResDto(articleRepository.save(article));
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}