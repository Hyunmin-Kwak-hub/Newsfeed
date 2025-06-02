package com.example.newsfeed.like.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "comment_like",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "comment_id"})}
)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    public CommentLike(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
        this.createdDateTime = LocalDateTime.now();
    }
}
