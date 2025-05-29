package com.example.newsfeed.article.service;

import com.example.newsfeed.global.exception.NotFoundException;
import com.example.newsfeed.global.exception.UnauthorizedException;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import com.example.newsfeed.article.controller.dto.ArticleReqDto;
import com.example.newsfeed.article.controller.dto.ArticleResDto;
import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.article.domain.repository.ArticleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleResDto createArticle(ArticleReqDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당하는 아이디가 없습니다."));
        Article article = new Article(user, requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl());
        return new ArticleResDto(articleRepository.save(article));
    }

    public Page<ArticleResDto> getAllArticles(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
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
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return articleRepository.findByUserId(userId, pageable)
                .map(ArticleResDto::new);
    }

    public ArticleResDto updateArticle(Long id, ArticleReqDto requestDto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        article.setTitle(requestDto.getTitle());
        article.setContent(requestDto.getContent());

        return new ArticleResDto(articleRepository.save(article));
    }

    @Transactional
    public void deleteArticle(Long id, Long userId) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("삭제할 게시글이 없습니다."));

        if (!article.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        }

        articleRepository.delete(article);
    }
}