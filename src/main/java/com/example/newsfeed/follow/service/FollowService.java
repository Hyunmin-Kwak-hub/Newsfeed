package com.example.newsfeed.follow.service;

import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.article.domain.repository.ArticleRepository;
import com.example.newsfeed.follow.controller.dto.FollowResDto;
import com.example.newsfeed.follow.controller.dto.FollowedUserArticleDto;
import com.example.newsfeed.follow.controller.dto.FollowedUserDto;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import com.example.newsfeed.follow.domain.entity.Follow;
import com.example.newsfeed.follow.domain.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public FollowResDto saveFollow(Long followingUserId, Long followedUserId) {

        // 팔로우 서비스 기능

        // 본인 정보 확인
        User followingUser = userRepository.findById(followingUserId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 요청자 없음"));

        // 팔로우 당하는 사람 정보 확인
        User followedUser = userRepository.findById(followedUserId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 없음"));

        Follow follow = new Follow(followingUser, followedUser);
        Follow savedFollow = followRepository.save(follow);

        return new FollowResDto(
                savedFollow.getId(),
                savedFollow.getFollowingUser().getId(),
                savedFollow.getFollowedUser().getId()
        );
    }

    public List<FollowedUserDto> getFollowedUsers(Long userId) {

        List<Follow> follows = followRepository.findAllByFollowingUserId(userId);

        return follows.stream()
                .map(follow -> new FollowedUserDto(
                        follow.getFollowedUser().getUsername(),
                        follow.getFollowedUser().getEmail()
                ))
                .collect(Collectors.toList());
    }

    public List<FollowedUserArticleDto> getFollowedUsersArticles(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowingUserId(userId);
        List<User> followedUsers = follows.stream()
                .map(Follow::getFollowedUser)
                .collect(Collectors.toList());

        List<Article> articles = articleRepository.findByUserInOrderByUpdatedDateTimeDesc(followedUsers);

        return articles.stream()
                .map(article -> new FollowedUserArticleDto(
                        article.getUser().getUsername(),
                        article.getUser().getEmail(),
                        article.getTitle(),
                        article.getContent(),
                        article.getImgUrl(),
                        article.getUpdatedDateTime()
                )).collect(Collectors.toList());
    }

    public void unfollow(Long followingUserId, Long followedUserId) {
        Follow follow = followRepository.findByFollowingUserIdAndFollowedUserId(followingUserId, followedUserId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));
        followRepository.delete(follow);
    }

}