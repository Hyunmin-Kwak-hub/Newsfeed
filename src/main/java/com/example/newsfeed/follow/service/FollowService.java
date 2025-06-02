package com.example.newsfeed.follow.service;

import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import com.example.newsfeed.follow.controller.dto.FollowReqDto;
import com.example.newsfeed.follow.controller.dto.FollowResDto;
import com.example.newsfeed.follow.domain.entity.Follow;
import com.example.newsfeed.follow.domain.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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
                savedFollow.getFollowing_user().getId(),
                savedFollow.getFollowed_user().getId()
        );
    }
}