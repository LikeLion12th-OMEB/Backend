package com.example.OMEB.domain.book.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "NaverBookDTO", description = "네이버 책 정보")
public class NaverBookDTO {
    @Schema(description = "책 제목", example = "자바의 정석")
    private String title;
    @Schema(description = "책 링크", example = "https://book-link.com")
    private String link;
    @Schema(description = "책 이미지 url", example = "https://book-image.com")
    private String image;
    @Schema(description = "저자", example = "남궁성")
    private String author;
    @Schema(description = "할인 가격", example = "20000")
    private String discount;
    @Schema(description = "출판사", example = "도우출판")
    private String publisher;
    @Schema(description = "ISBN", example = "9788968480011")
    private String isbn;
    @Schema(description = "책 설명", example = "자바의 정석은 자바를 배우기 위한 책입니다.")
    private String description;
    @Schema(description = "출판일", example = "2021-01-01")
    private String pubdate;

    public NaverBookDTO(String title, String link, String image, String author, String discount, String publisher, String isbn, String description, String pubdate) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.discount = discount;
        this.publisher = publisher;
        this.isbn = isbn;
        this.description = description;
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", discount='" + discount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", pubdate='" + pubdate + '\'' +
                '}';
    }
}