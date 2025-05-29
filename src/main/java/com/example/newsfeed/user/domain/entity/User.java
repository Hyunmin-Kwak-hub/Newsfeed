package com.example.newsfeed.user.domain.entity;

import com.example.newsfeed.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
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

    @Column(nullable = false)
    private Boolean deleted = false;

    public User() {}

    public User(String email, String password, String username, String info, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }

    // 회원 정보 수정
    public void updateUser(String userName, String info, String profileImgUrl) {
        this.username = userName;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateUserPassword(String password) {
        this.password = password;
    }

}