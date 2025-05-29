package com.example.newsfeed.user.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginReqDto {

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
            message = "이메일 형식에 맞아야 합니다.")
    private final String email;

    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자가 최소 1글자씩 있어야합니다. 길이는 8 ~ 20 입니다.")
    private final String password;

    public LoginReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}