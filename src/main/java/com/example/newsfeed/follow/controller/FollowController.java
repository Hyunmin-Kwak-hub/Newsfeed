package com.example.newsfeed.follow.controller;

import com.example.newsfeed.follow.controller.dto.FollowReqDto;
import com.example.newsfeed.follow.controller.dto.FollowResDto;
import com.example.newsfeed.follow.controller.dto.FollowedUserArticleDto;
import com.example.newsfeed.follow.controller.dto.FollowedUserDto;
import com.example.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userId}/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<FollowResDto> followUser(
            @PathVariable Long userId, // 본인 식별자
            @RequestBody FollowReqDto followReqDto // 상대방 식별자
    ) {

        FollowResDto resDto = followService.saveFollow(userId, followReqDto.getFollowedUserId());

        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FollowedUserDto>> getFollowedUsers(@PathVariable Long userId) {
        List<FollowedUserDto> followedUsers = followService.getFollowedUsers(userId);

        return new ResponseEntity<>(followedUsers, HttpStatus.OK);
    }

    @GetMapping("/articles")
    public List<FollowedUserArticleDto> getFollwedUsersArticles(@PathVariable Long userId) {
        return followService.getFollowedUsersArticles(userId);
    }

    @DeleteMapping("/unfollows/{followedId}")
    public ResponseEntity<String> unfollow(
            @PathVariable("userId") Long userId,
            @PathVariable("followedId") Long followedId) {
        followService.unfollow(userId, followedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}