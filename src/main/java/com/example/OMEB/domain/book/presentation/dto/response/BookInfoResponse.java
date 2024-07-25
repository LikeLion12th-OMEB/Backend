package com.example.OMEB.domain.book.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BookInfoResponse", description = "책 상세 정보 응답")
public class BookInfoResponse {
    @Schema(description = "책 id", example = "1")
    private Long bookId;
    @Schema(description = "책 제목", example = "자바의 정석")
    private String title;
    @Schema(description = "ISBN", example = "9788968480011")
    private String isbn;
    @Schema(description = "책 설명", example = "자바의 정석은 자바를 배우기 위한 책입니다.")
    private String description;
    @Schema(description = "저자", example = "남궁성")
    private String author;
    @Schema(description = "출판사", example = "도우출판")
    private String publisher;
    @Schema(description = "출판일", example = "2021-01-01")
    private String publicationDate;
    @Schema(description = "책 이미지 url", example = "https://book-image.com")
    private String bookImage;
    @Schema(description = "가격", example = "20000")
    private String price;
    @Schema(description = "판매 링크", example = "https://sell-link.com")
    private String sellLink;
    @Schema(description = "북마크 여부", example = "false")
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
