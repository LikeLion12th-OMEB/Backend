package com.example.OMEB.domain.bookmark.persistence.entity;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_mark")
@NoArgsConstructor
@Getter
public class BookMark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="book_id", nullable = false)
    private Book book;

    public BookMark(User user, Book book) {
        user.getBookMarks().add(this);
        this.user = user;
        book.getBookMarks().add(this);
        this.book = book;
    }

    public static BookMark createBookMark(User user, Book book) {
        return new BookMark(user, book);
    }
}
