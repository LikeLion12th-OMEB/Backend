package com.example.OMEB.domain.review.presentation.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "유저의 리뷰 페이지 응답")
public class UserReviewPageResponse {
	@Schema(description = "페이지 오프셋", example = "1")
	private int pageOffset;
	@Schema(description = "페이지 사이즈", example = "10")
	private int pageSize;
	@Schema(description = "총 페이지 수", example = "10")
	private int totalPage;
	@Schema(description = "리뷰 정보 리스트")
	private List<UserReviewResponse> userReviewPageResponseList;
	public UserReviewPageResponse(Page<UserReviewResponse> userReviewPageResponsePage) {
		this.pageOffset = userReviewPageResponsePage.getNumber() + 1;
		this.pageSize = userReviewPageResponsePage.getContent().size();
		this.totalPage = userReviewPageResponsePage.getTotalPages();
		this.userReviewPageResponseList = userReviewPageResponsePage.getContent();
	}

	public static UserReviewPageResponse of(Page<UserReviewResponse> userReviewPageResponsePage) {
		return new UserReviewPageResponse(userReviewPageResponsePage);
	}
}
