package com.example.OMEB.domain.user.persistence.entity;

import com.example.OMEB.global.base.domain.BaseEntity;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "expLog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;
}
