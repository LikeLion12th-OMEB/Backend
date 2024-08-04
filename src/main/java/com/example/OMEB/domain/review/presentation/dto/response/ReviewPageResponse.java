package com.example.OMEB.domain.review.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@Schema(description = "리뷰 페이지 응답")
public class ReviewPageResponse  {
    @Schema(description = "페이지 오프셋", example = "1")
    private int pageOffset;
    @Schema(description = "페이지 사이즈", example = "10")
    private int pageSize;
    @Schema(description = "총 페이지 수", example = "10")
    private int totalPage;
    @Schema(description = "리뷰 정보 리스트")
    private List<ReviewInfoResponse> reviewInfoResponseList;
    @Schema(description = "좋아요 순서 조회 여부")
    private boolean isLiked;
    public ReviewPageResponse(Page<ReviewInfoResponse> reviewInfoResponsePage,boolean isLiked) {
        this.pageOffset = reviewInfoResponsePage.getNumber() + 1;
        this.pageSize = reviewInfoResponsePage.getContent().size();
        this.totalPage = reviewInfoResponsePage.getTotalPages();
        this.reviewInfoResponseList = reviewInfoResponsePage.getContent();
        this.isLiked = isLiked;
    }
}
