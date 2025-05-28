package com.example.newsfeed.User.controller;

import com.example.newsfeed.User.controller.dto.*;
import com.example.newsfeed.User.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 회원 생성
    @PostMapping()
    public ResponseEntity<UserResDto> createUser(@Valid @RequestBody CreateUserReqDto reqDto) {
        UserResDto userResDto = userService.createUser(reqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    // 로그인

    // 로그아웃

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<List<UserListResDto>> findUserList() {
        List<UserListResDto> userResDtoList = userService.findUserList();
        return new ResponseEntity<>(userResDtoList, HttpStatus.OK);
    }

    //회원 단건 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResDto> findUserById(@PathVariable Long user_id) {
        UserResDto userResDto = userService.findUserById(user_id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    //회원 단건 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResDto> updateUser(
            @PathVariable Long user_id,
            @Valid @RequestBody UpdateUserReqDto reqDto
    ) {
        UserResDto userResDto = userService.updateUser(user_id, reqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    // 회원 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long user_id,
            @Valid @RequestBody DeleteUserReqDto reqDto
    ) {
        userService.deleteUser(user_id, reqDto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}