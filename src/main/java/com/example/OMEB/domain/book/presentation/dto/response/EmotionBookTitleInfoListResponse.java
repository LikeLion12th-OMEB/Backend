package com.example.OMEB.domain.book.presentation.dto.response;

import java.util.List;

import com.example.OMEB.domain.book.presentation.dto.BookTitleInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "EmotionBookTitleInfoListResponse", description = "감정 기반 책 제목 정보 리스트")
public class EmotionBookTitleInfoListResponse {
	@Schema(description = "감정", example = "ANGER")
	private String emotion;
	@Schema(description = "책 제목 정보 리스트", implementation = BookTitleInfo.class)
	private List<BookTitleInfo> bookTitleInfoList;

	public EmotionBookTitleInfoListResponse(String emotion, List<BookTitleInfo> bookTitleInfos) {
		this.emotion = emotion;
		this.bookTitleInfoList = bookTitleInfos;
	}
}
