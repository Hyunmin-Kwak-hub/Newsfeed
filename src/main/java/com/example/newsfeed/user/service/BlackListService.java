package com.example.newsfeed.user.service;

import com.example.newsfeed.user.domain.entity.BlackList;
import com.example.newsfeed.user.domain.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "BlackList")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlackListService {

    private final BlackListRepository blackListRepository;

    // 생성
    @Transactional
    public void addBlackList(String token) {
        if (!isExistBlackList(token)) {
            blackListRepository.save(new BlackList(token));
            log.info("블랙리스트 추가: token={}", token);
        }
    }

    // 조회
    public boolean isExistBlackList(String token) {
        return blackListRepository.existsBlackListByToken(token);
    }

}