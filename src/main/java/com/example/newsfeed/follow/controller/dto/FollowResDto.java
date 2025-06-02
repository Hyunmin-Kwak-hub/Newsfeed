package com.example.newsfeed.follow.controller.dto;

import lombok.Getter;

@Getter
public class FollowResDto {

    private final Long id;

    private final Long followingUserId;

    private final Long followedUserId;

    public FollowResDto(Long id, Long followingUserId, Long followedUserId) {
        this.id = id;
        this.followingUserId = followingUserId;
        this.followedUserId = followedUserId;
    }
}