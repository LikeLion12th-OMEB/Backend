package com.example.OMEB.domain.user.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class History extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="book_id", nullable = false)
    private Book book;

    @Builder
    public History(String text, Book book, User user) {
        this.text = text;
        this.book = book;
        this.user = user;
    }
}
