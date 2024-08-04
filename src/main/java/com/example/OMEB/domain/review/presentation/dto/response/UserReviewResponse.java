package com.example.OMEB.domain.review.presentation.dto.response;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Schema(name = "UserReviewResponse", description = "유저 리뷰 정보 응답")
public class UserReviewResponse {
    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;
    @Schema(description = "리뷰 내용", example = "이 책은 정말 재밌어요!")
    private String reviewContent;
    @Schema(description = "리뷰 감정 태그", example = "HAPPINESS")
    private String reviewTag;
    @Schema(description = "좋아요 수", example = "0")
    private Long likeCount;
    @Schema(description = "책 ID", example = "1")
    private Long bookId;
    @Schema(description = "책 제목", example = "1")
    private String bookTitle;
    @Schema(description = "책 이미지", example = "https://s3.ap-northeast-2.amazonaws.com/omeb/profile/1.jpg")
    private String bookImage;
    @Schema(description = "리뷰 생성일", example = "2021-08-01T00:00:00")
    private String createdAt;
    @Schema(description = "리뷰 수정일", example = "2021-08-01T00:00:00")
    private String updatedAt;

    public UserReviewResponse(Long reviewId, String reviewContent, TagName reviewTag, Long likeCount, Long bookId,
        String bookTitle, String bookImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewTag = reviewTag.toString();
        this.likeCount = likeCount;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toString() : null;
    }
}
