package com.example.newsfeed.User.service;

import com.example.newsfeed.User.controller.dto.CreateUserReqDto;
import com.example.newsfeed.User.controller.dto.UpdateUserReqDto;
import com.example.newsfeed.User.controller.dto.UserListResDto;
import com.example.newsfeed.User.controller.dto.UserResDto;
import com.example.newsfeed.User.domain.entity.User;
import com.example.newsfeed.User.domain.repository.UserRepository;
import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.exception.NotFoundException;
import com.example.newsfeed.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResDto createUser(CreateUserReqDto reqDto) {
        String encodedPassword = passwordEncoder.encode(reqDto.getPassword());
        User user = new User(reqDto.getEmail(), encodedPassword, reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());
        return new UserResDto(userRepository.save(user));
    }

    public List<UserListResDto> findUserList() {
        return userRepository.findAll().stream().map(UserListResDto::new).toList();
    }

    public UserResDto findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. userId = " + userId));
        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUser(Long userId, UpdateUserReqDto reqDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. userId = " + userId));
        checkUserPassword(reqDto.getPassword(), user);

        String encodedPassword = passwordEncoder.encode(reqDto.getNewPassword());
        user.updateUser(encodedPassword, reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());

        return new UserResDto(user);
    }

    public void deleteUser(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. userId = " + userId));
        checkUserPassword(password, user);
        userRepository.delete(user);
    }

    private void checkUserPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }


}