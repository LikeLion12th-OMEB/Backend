package com.example.OMEB.domain.book.persistence.entity;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.persistence.entity.BookMark;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String publicationDate;

    @Column(nullable = false)
    private String bookImage;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String sellLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tag_id", nullable = false)
    private Tag tag;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Book(String title, String description, String author, String publisher, String publicationDate, String bookImage, String price, String sellLink, Tag tag) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.bookImage = bookImage;
        this.price = price;
        this.sellLink = sellLink;
        this.tag = tag;
    }
}
