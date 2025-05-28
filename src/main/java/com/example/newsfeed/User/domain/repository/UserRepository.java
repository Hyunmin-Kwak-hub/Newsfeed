package com.example.newsfeed.User.domain.repository;

import com.example.newsfeed.User.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);
}