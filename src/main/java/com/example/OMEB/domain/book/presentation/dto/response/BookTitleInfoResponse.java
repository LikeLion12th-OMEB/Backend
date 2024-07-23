package com.example.OMEB.domain.book.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTitleInfoResponse {
    private Long bookId;
    private String title;
    private String author;
    private String bookImage;
    private String price;

    public BookTitleInfoResponse(Long bookId, String title, String author, String bookImage, String price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.bookImage = bookImage;
        this.price = price;
    }
}
