package com.example.newsfeed.user.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserReqDto {

    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자가 최소 1글자씩 있어야합니다. 길이는 8 ~ 20 입니다.")
    private final String password;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$",
            message = "이름은 영문자, 한글, 숫자만 사용가능하며, 길이는 2 ~ 10 입니다.")
    private final String username;

    private final String info;

    private final String profileImgUrl;

}