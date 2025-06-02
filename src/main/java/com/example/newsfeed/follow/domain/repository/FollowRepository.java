package com.example.newsfeed.follow.domain.repository;

import com.example.newsfeed.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우한 사람들 (user_id가 FollowingUser.id인 것들)
    List<Follow> findAllByFollowingUserId(Long followingUserId);
}