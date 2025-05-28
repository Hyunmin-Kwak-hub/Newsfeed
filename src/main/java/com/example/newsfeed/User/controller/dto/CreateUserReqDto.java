package com.example.newsfeed.User.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateUserReqDto {

    @NotEmpty()
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
            message = "이메일 형식에 맞아야 합니다.")
    private final String email;

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 최소 각각 1개씩은 있어야합니다. 길이는 4 ~ 20 입니다.")
    private final String password;

    @NotEmpty()
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "이름은 영문자, 한글, 숫자만 사용가능하며, 길이는 1 이상 10 이하 입니다.")
    private final String userName;

    private final String info;

    private final String profileImgUrl;

    public CreateUserReqDto(String email, String password, String userName, String info, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }

}