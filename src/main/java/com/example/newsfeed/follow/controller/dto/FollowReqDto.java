package com.example.newsfeed.follow.controller.dto;

import lombok.Getter;

@Getter
public class FollowReqDto {

    private final Long followedUserId;

    public FollowReqDto(Long followedUserId) {
        this.followedUserId = followedUserId;
    }
}