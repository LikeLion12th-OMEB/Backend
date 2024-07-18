package com.example.OMEB.domain.user.persistence.entity;

import com.example.OMEB.global.base.domain.BaseEntity;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private OAuth2Provider provider;

    @ColumnDefault("1")
    private Integer level;

    @ColumnDefault("0")
    private Integer exp;

}
