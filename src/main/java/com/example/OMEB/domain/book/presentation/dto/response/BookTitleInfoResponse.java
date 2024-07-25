package com.example.OMEB.domain.book.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BookTitleListResponse", description = "책 제목 리스트 응답")
public class BookTitleInfoResponse {
    @Schema(description = "책 id", example = "1")
    private Long bookId;
    @Schema(description = "책 제목", example = "자바의 정석")
    private String title;
    @Schema(description = "저자", example = "남궁성")
    private String author;
    @Schema(description = "책 이미지 url", example = "https://book-image.com")
    private String bookImage;
    @Schema(description = "가격", example = "20000")
    private String price;

    public BookTitleInfoResponse(Long bookId, String title, String author, String bookImage, String price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.bookImage = bookImage;
        this.price = price;
    }
}
