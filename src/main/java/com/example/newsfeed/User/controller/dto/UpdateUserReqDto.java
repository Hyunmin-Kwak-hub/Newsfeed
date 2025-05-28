package com.example.newsfeed.User.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserReqDto {

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 최소 각각 1개씩은 있어야합니다. 길이는 4 ~ 20 입니다.")
    private final String password;

    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 최소 각각 1개씩은 있어야합니다. 길이는 4 ~ 20 입니다.")
    private final String newPassword;

    // 회원정보 수정 : 이름, 비밀번호, 소개글, 프로필 사진
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "이름은 영문자, 한글, 숫자만 사용가능하며, 길이는 1 이상 10 이하 입니다.")
    private final String userName;

    private final String info;

    private final String profileImgUrl;

    public UpdateUserReqDto(String password, String newPassword, String userName, String info, String profileImgUrl) {
        this.password = password;
        this.newPassword = newPassword;
        this.userName = userName;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }
}