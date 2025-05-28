package com.example.newsfeed.User.service;

import com.example.newsfeed.User.controller.dto.*;
import com.example.newsfeed.User.domain.entity.User;
import com.example.newsfeed.User.domain.repository.UserRepository;
import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.exception.BadRequestException;
import com.example.newsfeed.global.exception.ConflictException;
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
        boolean existsEmail = userRepository.existsUserByEmail(reqDto.getEmail());
        if (existsEmail) {
            throw new ConflictException("이미 가입된 이메일입니다.");
        }
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

        user.updateUser(reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());

        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUserPassword(Long userId, UpdateUserPasswordReqDto reqDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. userId = " + userId));
        checkUserPassword(reqDto.getPassword(), user);

        if(reqDto.getPassword().equals(reqDto.getNewPassword())) {
            throw new BadRequestException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다. ");
        }

        String encodedPassword = passwordEncoder.encode(reqDto.getNewPassword());
        user.updateUserPassword(encodedPassword);

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