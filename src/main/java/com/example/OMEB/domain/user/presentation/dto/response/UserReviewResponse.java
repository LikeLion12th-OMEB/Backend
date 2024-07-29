package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.persistence.entity.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserReviewResponse", description = "유저 리뷰 정보 응답")
public class UserReviewResponse {
    @Schema(description = "리뷰 DB id", example = "1")
    private Long reviewId;
    @Schema(description = "책 제목", example = "1")
    private String bookTitle;
    @Schema(description = "리뷰 내용", example = "이 책은 정말 재밌어요!")
    private String content;
    @Schema(description = "리뷰 감정 태그", example = "HAPPINESS")
    private Tag tag;
    @Schema(description = "좋아요 수", example = "0")
    private int likeCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 생성일", example = "2021-08-01T00:00:00")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 수정일", example = "2021-08-01T00:00:00")
    private LocalDateTime updatedAt;

    public static UserReviewResponse entityToResponse(Review review){
        return UserReviewResponse.builder()
                .bookTitle(review.getBook().getTitle())
                .content(review.getContent())
                .tag(review.getTag())
                .likeCount(review.getLikes().size())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
