package com.example.newsfeed.User.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class DeleteUserReqDto {

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 최소 각각 1개씩은 있어야합니다. 길이는 4 ~ 20 입니다.")
    private final String password;

    public DeleteUserReqDto(String password) {
        this.password = password;
    }
}