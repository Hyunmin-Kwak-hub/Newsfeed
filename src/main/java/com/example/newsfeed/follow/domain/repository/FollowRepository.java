package com.example.newsfeed.follow.domain.repository;

import com.example.newsfeed.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

}