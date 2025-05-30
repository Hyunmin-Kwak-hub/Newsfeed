package com.example.newsfeed.user.controller.dto;

import com.example.newsfeed.user.domain.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResDto {

    private final String userName;
    private final String info;
    private final String profileImgUrl;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    public UserResDto(User user) {
        this.userName = user.getUsername();
        this.info = user.getInfo();
        this.profileImgUrl = user.getProfileImgUrl();
        this.createdDateTime = user.getCreatedDateTime();
        this.updatedDateTime = user.getUpdatedDateTime();
    }
}