package com.example.newsfeed.article.service;

import com.example.newsfeed.global.exception.NotFoundException;
import com.example.newsfeed.global.exception.UnauthorizedException;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.article.domain.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ArticleResDto createArticle(Long userId, ArticleReqDto requestDto) {
        if (userId == null) {
            throw new UnauthorizedException("로그인이 필요한 기능입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이디가 없습니다."));
        Article article = new Article(user, requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl());
        return new ArticleResDto(articleRepository.save(article));
    }

    public Page<ArticleResDto> getAllArticles(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDateTime"));
        Page<Article> articles = articleRepository.findAll(pageable);

        if (articles.isEmpty()) {
            throw new NotFoundException("작성된 게시글이 없습니다.");
        }

        return articles.map(ArticleResDto::new);
    }

    public ArticleResDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        return new ArticleResDto(article);
    }

    public Page<ArticleResDto> getArticlesByUser(Long userId, int page) {
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDateTime"));

        Page<Article> articles = articleRepository.findByUserId(userId, pageable);
        if(articles.isEmpty()) {
            throw new NotFoundException("해당 id가 작성한 게시글이 없습니다.");
        }

        return articleRepository.findByUserId(userId, pageable)
                .map(ArticleResDto::new);
    }

    public Page<ArticleResDto> getArticlesByDateBetween(LocalDate startDate, LocalDate endDate, int page) {
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDateTime"));

        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Page<Article> articles = articleRepository.findByCreatedDateTimeBetween(startDateTime, endDateTime, pageable);

        if (articles.isEmpty()) {
            throw new NotFoundException("해당 기간에 작성된 게시글이 없습니다.");
        }
        return articles.map(ArticleResDto::new);
    }

    @Transactional
    public ArticleResDto updateArticle(Long id, Long userId, ArticleReqDto requestDto) {
        if (userId == null) {
            throw new UnauthorizedException("로그인이 필요한 기능입니다.");
        }

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        if (!article.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        article.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl());
        return new ArticleResDto(articleRepository.save(article));
    }

    @Transactional
    public void deleteArticle(Long id, Long userId) {
        if (userId == null) {
            throw new UnauthorizedException("로그인이 필요한 기능입니다.");
        }

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        if (!article.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }

        articleRepository.delete(article);
    }
}