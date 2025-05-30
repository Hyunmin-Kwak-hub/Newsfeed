package com.example.newsfeed.user.service;

import com.example.newsfeed.global.config.JwtUtil;
import com.example.newsfeed.global.dto.AuthUserDto;
import com.example.newsfeed.user.controller.dto.*;
import com.example.newsfeed.user.domain.entity.User;
import com.example.newsfeed.user.domain.repository.UserRepository;
import com.example.newsfeed.global.config.PasswordEncoder;
import com.example.newsfeed.global.exception.BadRequestException;
import com.example.newsfeed.global.exception.ConflictException;
import com.example.newsfeed.global.exception.NotFoundException;
import com.example.newsfeed.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResDto createUser(CreateUserReqDto reqDto) {
        boolean existsEmail = userRepository.existsUserByEmail(reqDto.getEmail());
        if (existsEmail) {
            throw new ConflictException("이미 가입된 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(reqDto.getPassword());
        User user = new User(reqDto.getEmail(), encodedPassword, reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());
        return new UserResDto(userRepository.save(user));
    }

    public LoginResDto login(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .filter(find -> !find.getDeleted())
                .orElseThrow(() -> new NotFoundException("이메일이 일치하지 않습니다."));
        checkUserPassword(password, user);
        String token = jwtUtil.createToken(user.getId(), user.getUsername());
        return new LoginResDto(token, user.getUsername());
    }

    public void logout(String password) {
        // 본인 확인
        User user = findUserBySecurity();
        checkUserPassword(password, user);
    }

    public List<UserListResDto> findUserList(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .filter(find -> !find.getDeleted()).map(UserListResDto::new).toList();
    }

    public UserResDto findUserById(Long userId) {
        User user = findNotDeletedUserById(userId);
        return new UserResDto(user);
    }

    private User findNotDeletedUserById(Long userId) {
        return userRepository.findById(userId)
                .filter(find -> !find.getDeleted())
                .orElseThrow(() ->new NotFoundException("유저가 존재하지 않습니다. userId = " + userId));
    }

    private User findUserBySecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((AuthUserDto) authentication.getPrincipal()).getId();
        return findNotDeletedUserById(id);
    }

    @Transactional
    public UserResDto updateUser(UpdateUserReqDto reqDto) {
        User user = findUserBySecurity();
        checkUserPassword(reqDto.getPassword(), user);

        user.updateUser(reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());

        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUserPassword(UpdateUserPasswordReqDto reqDto) {
        User user = findUserBySecurity();
        checkUserPassword(reqDto.getPassword(), user);

        if(reqDto.getPassword().equals(reqDto.getNewPassword())) {
            throw new BadRequestException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다. ");
        }

        String encodedPassword = passwordEncoder.encode(reqDto.getNewPassword());
        user.updateUserPassword(encodedPassword);

        return new UserResDto(user);
    }

    @Transactional
    public void deleteUser(String password) {
        User user = findUserBySecurity();
        checkUserPassword(password, user);
        userRepository.delete(user);
    }

    private void checkUserPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }


}