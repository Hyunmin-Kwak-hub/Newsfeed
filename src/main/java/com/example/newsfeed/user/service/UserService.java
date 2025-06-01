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

@Slf4j(topic = "User")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private void callLogInfo(String message, User user) {
        log.info("{}: username={}, email={}", message, user.getUsername(), user.getEmail());
    }

    private void callLogError(String message, User user) {
        log.error("{}: username={}, email={}", message, user.getUsername(), user.getEmail());
    }

    // 생성
    @Transactional
    public UserResDto createUser(CreateUserReqDto reqDto) {
        // 회원 확인
        boolean existsEmail = userRepository.existsUserByEmail(reqDto.getEmail());
        if (existsEmail) {
            log.error("이미 가입된 이메일입니다. email={}", reqDto.getEmail());
            throw new ConflictException("이미 가입된 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(reqDto.getPassword());
        // 회원가입
        User user = new User(reqDto.getEmail(), encodedPassword, reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());
        callLogInfo("회원가입", user);
        return new UserResDto(userRepository.save(user));
    }

    public LoginResDto login(String email, String password) {
        // 본인 확인
        User user = userRepository.findUserByEmail(email)
                .filter(find -> !find.getDeleted())
                .orElseThrow(() -> {
                    log.error("이메일이 존재하지 않습니다. email={}", email);
                    return new NotFoundException("이메일이 일치하지 않습니다.");
                });
        checkUserPassword(password, user);
        // 로그인
        String token = jwtUtil.createToken(user.getId(), user.getUsername());
        callLogInfo("로그인", user);
        return new LoginResDto(token, user.getUsername());
    }

    public void logout(String password) {
        // 본인 확인
        User user = findUserBySecurity();
        checkUserPassword(password, user);

        callLogInfo("로그아웃", user);
    }

    // 조회
    public List<UserListResDto> findUserList(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .filter(find -> !find.getDeleted())
                .map(UserListResDto::new)
                .toList();
    }

    public UserResDto findUserById(Long userId) {
        User user = findNotDeletedUserById(userId);
        return new UserResDto(user);
    }

    private User findNotDeletedUserById(Long userId) {
        return userRepository.findById(userId)
                .filter(find -> !find.getDeleted())
                .orElseThrow(() -> {
                    log.error("유저를 찾을 수 없습니다. userId={}", userId);
                    return new NotFoundException("유저를 찾을 수 없습니다. userId=" + userId);
                });
    }

    private User findUserBySecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((AuthUserDto) authentication.getPrincipal()).getId();
        return findNotDeletedUserById(id);
    }

    // 수정
    @Transactional
    public UserResDto updateUser(UpdateUserReqDto reqDto) {
        // 본인 확인
        User user = findUserBySecurity();
        checkUserPassword(reqDto.getPassword(), user);

        // 회원 정보 수정
        user.updateUser(reqDto.getUserName(), reqDto.getInfo(), reqDto.getProfileImgUrl());

        callLogInfo("회원 정보 수정", user);
        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUserPassword(UpdateUserPasswordReqDto reqDto) {
        // 본인 확인
        User user = findUserBySecurity();
        checkUserPassword(reqDto.getPassword(), user);

        // 비밀번호 변경
        if (reqDto.getPassword().equals(reqDto.getNewPassword())) {
            callLogError("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.", user);
            throw new BadRequestException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
        }

        String encodedPassword = passwordEncoder.encode(reqDto.getNewPassword());
        user.updateUserPassword(encodedPassword);

        callLogInfo("비밀번호 변경", user);
        return new UserResDto(user);
    }

    // 삭제
    @Transactional
    public void deleteUser(String password) {
        // 본인 확인
        User user = findUserBySecurity();
        checkUserPassword(password, user);
        // 회원탈퇴
        userRepository.delete(user);
        callLogInfo("회원탈퇴", user);
    }

    private void checkUserPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            callLogError("비밀번호가 일치하지 않습니다.", user);
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }


}