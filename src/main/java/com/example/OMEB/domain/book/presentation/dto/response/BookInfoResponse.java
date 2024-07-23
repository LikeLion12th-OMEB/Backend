package com.example.OMEB.domain.book.presentation.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfoResponse {
    private Long bookId;
    private String title;
    private String isbn;
    private String description;
    private String author;
    private String publisher;
    private String publicationDate;
    private String bookImage;
    private String price;
    private String sellLink;
    private Boolean isBookMarked;
    @Builder
    public BookInfoResponse(Long bookId, String title, String isbn, String description, String author, String publisher, String publicationDate, String bookImage, String price, String sellLink, Boolean isBookMarked) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.bookImage = bookImage;
        this.price = price;
        this.sellLink = sellLink;
        this.isBookMarked = isBookMarked;
    }
}
