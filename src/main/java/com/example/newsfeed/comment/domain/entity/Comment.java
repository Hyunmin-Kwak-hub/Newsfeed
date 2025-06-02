package com.example.newsfeed.comment.domain.entity;

import com.example.newsfeed.article.domain.entity.Article;
import com.example.newsfeed.user.domain.entity.User;
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

    // 기본 키: 댓글 ID (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관관계: 댓글은 하나의 게시글에 소속 (다대일)
    @ManyToOne(fetch = FetchType.LAZY)  //일대다
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // 연관관계: 댓글은 한 명의 사용자에 의해 작성됨 (다대일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 댓글 내용
    @Column(length = 100, nullable = false)
    private String content;

    // 댓글 생성 시간
    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    // 댓글 수정 시간
    @Column
    private LocalDateTime updatedDateTime;

    // 댓글 저장 전 실행되는 기능
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        this.createdDateTime = now;
        this.updatedDateTime = now;
    }

    // 댓글 수정 전 실행되는 기능
    @PreUpdate
    public void onUpdate() {
        this.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    // 댓글 내용 수정
    public void updateContent(String newContent) {
        this.content = newContent;
    }


}
