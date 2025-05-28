package com.example.newsfeed.User.domain.entity;

import com.example.newsfeed.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(length = 100)
    private String info;

    @Column(name = "profile_img_url", columnDefinition = "text")
    private String profileImgUrl;

    public User() {}

    public User(String email, String password, String username, String info, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateUser(String password, String userName, String info, String profileImgUrl) {
        this.password = password;
        this.username = userName;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }

}