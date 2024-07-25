package com.example.OMEB.domain.book.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Schema(name = "BookTitleListResponse", description = "책 제목 리스트 응답")
public class BookTitleListResponse {
    private List<BookTitleInfoResponse> bookTitleInfoResponseList;

    public BookTitleListResponse(List<BookTitleInfoResponse> bookTitleInfoResponseList) {
        this.bookTitleInfoResponseList = bookTitleInfoResponseList;
    }
}
