package com.example.OMEB.domain.bookmark.presentation.dto.response;

import java.util.List;

import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "BookTitleListResponse", description = "책 제목 리스트 응답")
public class BookMarkBookTitleListResponse {
    @Schema(description = "책 제목 정보 리스트",implementation = BookTitleInfoResponse.class)
    private List<BookTitleInfoResponse> bookTitleInfoResponseList;

    public BookMarkBookTitleListResponse(List<BookTitleInfoResponse> bookTitleInfoResponseList) {
        this.bookTitleInfoResponseList = bookTitleInfoResponseList;
    }
}
