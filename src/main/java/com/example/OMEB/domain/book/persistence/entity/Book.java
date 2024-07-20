package com.example.OMEB.domain.book.persistence.entity;

import com.example.OMEB.domain.user.persistence.entity.BookMark;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String publication_date;

    @Column(nullable = false)
    private String book_image;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String sell_link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tag_id", nullable = false)
    private Tag tag;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<BookMark> bookMarks = new ArrayList<>();

}
