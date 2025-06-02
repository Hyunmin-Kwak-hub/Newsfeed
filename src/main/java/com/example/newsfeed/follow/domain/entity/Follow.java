package com.example.newsfeed.follow.domain.entity;

import com.example.newsfeed.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "follows")
public class Follow {

    // 팔로우 id (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 팔로우한 사람 (본인)
    @ManyToOne
    @JoinColumn(name = "following_user_id")
    private User followingUser;

    // 팔로우 당한 사람 (친구)
    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

    public Follow() {}

    public Follow(User followingUser, User followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }

}