package com.example.OMEB.domain.user.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.BookMark;
import com.example.OMEB.domain.review.persistence.entity.Like;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.global.base.domain.BaseEntity;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private String providerId;

    @ColumnDefault("1")
    private Integer level;

    @ColumnDefault("0")
    private Integer exp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Profile> profiles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ExpLog> expLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BookMark> bookMarks = new ArrayList<>();


    @Builder
    public User(String nickname, OAuth2Provider provider, Integer level) {
        this.nickname = nickname;
        this.provider = provider;
        this.level = level;
    }

    public User(OAuth2Provider provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }
}
