package com.example.newsfeed.user.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserPasswordReqDto {

    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자가 최소 1글자씩 있어야합니다. 길이는 8 ~ 20 입니다.")
    private final String password;

    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자가 최소 1글자씩 있어야합니다. 길이는 8 ~ 20 입니다.")
    private final String newPassword;

}