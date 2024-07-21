package com.example.OMEB.domain.review.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviewLike")
@NoArgsConstructor
@Getter
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="review_id", nullable = false)
    private Review review;

    public Like(User user, Review review) {
        review.getLikes().add(this);
        user.getLikes().add(this);
        this.user = user;
        this.review = review;
    }
}
