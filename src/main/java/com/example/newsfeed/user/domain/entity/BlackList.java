package com.example.newsfeed.user.domain.entity;

import com.example.newsfeed.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "black_list")
public class BlackList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String token;

    public BlackList() {}

    public BlackList(String token) {
        this.token = token;
    }

}