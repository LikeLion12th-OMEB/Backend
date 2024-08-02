package com.example.OMEB.domain.user.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.BookMark;
import com.example.OMEB.domain.profile.application.ProfileUrlUtill;
import com.example.OMEB.domain.review.persistence.entity.Like;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.application.IncreaseExpType;
import com.example.OMEB.global.base.domain.BaseEntity;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private String providerId;

    @ColumnDefault("1")
    private Integer level;

    @ColumnDefault("0")
    private Integer exp;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    private LocalDate lastLoginAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @OrderBy("createdAt desc")
    private List<ExpLog> expLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BookMark> bookMarks = new ArrayList<>();

    // 보류
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public void updateNickname(String nickname){this.nickname = nickname;}
    public void updateLevel(Integer level){this.level = level;}
    public void updateExp(Integer exp){this.exp = exp;}
    public void updateLastLoginAt(){this.lastLoginAt = LocalDate.now();}

    @Builder
    public User(String nickname, OAuth2Provider provider, Integer level) {
        this.nickname = nickname;
        this.provider = provider;
        this.level = level;
        this.profileImageUrl = "https://omeb-image.s3.ap-northeast-2.amazonaws.com/default_profile.png";
    }
    public User(OAuth2Provider provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
        this.profileImageUrl = "https://omeb-image.s3.ap-northeast-2.amazonaws.com/default_profile.png";
    }
}
