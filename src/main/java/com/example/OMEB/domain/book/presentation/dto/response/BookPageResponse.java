package com.example.OMEB.domain.book.presentation.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "책 페이지 응답")
public class BookPageResponse {
	@Schema(description = "페이지 오프셋", example = "1")
	private int pageOffset;
	@Schema(description = "페이지 사이즈", example = "10")
	private int pageSize;
	@Schema(description = "총 페이지 수", example = "10")
	private int totalPage;
	@Schema(description = "책 정보 리스트")
	private List<BookTitleInfoResponse> bookTitleInfoResponseLists;

	public BookPageResponse(Page<BookTitleInfoResponse> bookTitleInfoResponsePage) {
		this.pageOffset = bookTitleInfoResponsePage.getNumber() + 1;
		this.pageSize = bookTitleInfoResponsePage.getContent().size();
		this.totalPage = bookTitleInfoResponsePage.getTotalPages();
		this.bookTitleInfoResponseLists = bookTitleInfoResponsePage.getContent();
	}
}
