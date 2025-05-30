package com.example.newsfeed.like.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "article_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "article_id"}),

        }
)
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "article_id", nullable = false)
    private Long articleId;
    //article 직접참조 가능하도록 변경요망 객체간 연관관계 학습요망

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createdDateTime;


    public ArticleLike(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
        this.createdDateTime = LocalDateTime.now();
    }



}

