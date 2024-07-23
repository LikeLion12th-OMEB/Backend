package com.example.OMEB.domain.book.persistence.entity;

import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.persistence.entity.BookMark;
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

    @Column(nullable = false)
    private String isbn;

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


    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Book(String title, String isbn, String description, String author, String publisher, String publicationDate, String bookImage, String price, String sellLink,  List<BookMark> bookMarks, List<Review> reviews) {
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.bookImage = bookImage;
        this.price = price;
        this.sellLink = sellLink;
        this.bookMarks = bookMarks;
        this.reviews = reviews;
    }

    public static Book fromNaverBookDTO(NaverBookDTO naverBookDTO) {
        return Book.builder()
                .title(naverBookDTO.getTitle())
                .isbn(naverBookDTO.getIsbn())
                .description(naverBookDTO.getDescription())
                .author(naverBookDTO.getAuthor())
                .publisher(naverBookDTO.getPublisher())
                .publicationDate(naverBookDTO.getPubdate())
                .bookImage(naverBookDTO.getImage())
                .price(naverBookDTO.getDiscount())
                .sellLink(naverBookDTO.getLink())
                .build();
    }

    public static BookInfoResponse toBookInfoResponse(Book book) {
        return BookInfoResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .bookImage(book.getBookImage())
                .price(book.getPrice())
                .sellLink(book.getSellLink())
                .build();
    }
}
