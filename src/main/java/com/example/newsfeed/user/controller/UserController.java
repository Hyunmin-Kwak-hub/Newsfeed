package com.example.newsfeed.user.controller;

import com.example.newsfeed.global.common.Const;
import com.example.newsfeed.global.config.JwtUtil;
import com.example.newsfeed.user.controller.dto.*;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.service.UserService;
import com.example.newsfeed.user.service.BlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BlackListService blackListService;
    private final JwtUtil jwtUtil;

    // 회원 생성
    @PostMapping()
    public ResponseEntity<UserResDto> createUser(@Valid @RequestBody CreateUserReqDto reqDto) {
        UserResDto userResDto = userService.saveUser(reqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto reqDto) {
        LoginResDto loginResDto = userService.loginUser(reqDto.getEmail(), reqDto.getPassword());
        return new ResponseEntity<>(loginResDto, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        String bearerJwt = request.getHeader(Const.AUTHORIZATION_HEADER);
        String jwt = jwtUtil.substringToken(bearerJwt);
        blackListService.addBlackList(jwt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<List<UserListResDto>> findUserList(
            @PageableDefault(sort = "updatedDateTime", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        List<UserListResDto> userResDtoList = userService.getUserList(pageable);
        return new ResponseEntity<>(userResDtoList, HttpStatus.OK);
    }

    // 회원 단건 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResDto> findUserById(@PathVariable Long user_id) {
        UserResDto userResDto = userService.getUserById(user_id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    // 회원 단건 수정
    @PutMapping()
    public ResponseEntity<UserResDto> updateUserInfo(@Valid @RequestBody UpdateUserReqDto reqDto) {
        UserResDto userResDto = userService.setUserInfo(reqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    // 회원 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<UserResDto> updateUserPassword(@Valid @RequestBody UpdateUserPasswordReqDto reqDto) {
        UserResDto userResDto = userService.setUserPassword(reqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping()
    public ResponseEntity<Void> withdrawUser (
            @Valid @RequestBody DeleteUserReqDto reqDto,
            HttpServletRequest request
    ) {
        User user = userService.deleteUser(reqDto.getPassword());
        // 회원탈퇴 후 로그아웃 진행
        String bearerJwt = request.getHeader(Const.AUTHORIZATION_HEADER);
        String jwt = jwtUtil.substringToken(bearerJwt);
        blackListService.addBlackList(jwt);

        userService.callLogInfo("회원탈퇴", user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}