package com.example.newsfeed.comment.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long articleId;

    // 연관관계: Article
    @ManyToOne(fetch = FetchType.LAZY)  //일대다
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // 연관관계: User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column
    private LocalDateTime updatedDateTime;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        this.createdDateTime = now;
        this.updatedDateTime = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }


}
