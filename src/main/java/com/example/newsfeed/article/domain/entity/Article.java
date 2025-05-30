package com.example.newsfeed.article.domain.entity;

import com.example.newsfeed.global.common.BaseTimeEntity;
import com.example.newsfeed.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "article")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String content;

    @Setter
    @Column(nullable = false)
    private String imgUrl;

    public Article() {}

    public Article(User user, String title, String content, String imgUrl) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public void update(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}