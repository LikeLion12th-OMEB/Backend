package com.example.OMEB.domain.review.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.Tag;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tag_id", nullable = false)
    private Tag tag;

    @Builder
    public Review(User user, Book book, String content, Tag tag) {
        this.user = user;
        this.book = book;
        this.content = content;
        this.tag = tag;
    }

    public static Review fromReviewRequest(User user, Book book, ReviewCreateRequest reviewCreateRequest,Tag tag) {
        Review review = Review.builder()
                .content(reviewCreateRequest.getContent())
                .user(user)
                .book(book)
                .tag(tag)
                .build();

        tag.getReviews().add(review);
        user.getReviews().add(review);

        return review;
    }

    public void updateReview(String content,Tag tag) {
        this.content = content;
        tag.getReviews().add(this);
        this.tag = tag;
    }
}
