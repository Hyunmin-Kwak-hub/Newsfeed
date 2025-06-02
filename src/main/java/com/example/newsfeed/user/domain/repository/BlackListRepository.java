package com.example.newsfeed.user.domain.repository;


import com.example.newsfeed.user.domain.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsBlackListByToken(String token);
}