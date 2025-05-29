package com.example.newsfeed.user.controller.dto;

import com.example.newsfeed.user.domain.entity.User;
import lombok.Getter;

@Getter
public class UserListResDto {

    private final String userName;
    private final String info;
    private final String profileImgUrl;

    public UserListResDto(User user) {
        this.userName = user.getUsername();
        this.info = user.getInfo();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}