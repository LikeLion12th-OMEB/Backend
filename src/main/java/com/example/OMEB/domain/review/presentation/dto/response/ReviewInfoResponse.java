package com.example.OMEB.domain.review.presentation.dto.response;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "리뷰 정보 응답")
public class ReviewInfoResponse {
    @Schema(description = "책 ID", example = "1")
    private Long bookId;
    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;
    @Schema(description = "유저 닉네임", example = "강민기")
    private String userNickname;
    @Schema(description = "유저 프로필 이미지" , example = "https://s3.ap-northeast-2.amazonaws.com/omeb/profile/1.jpg")
    private String userProfileImage;
    @Schema(description = "리뷰 내용", example = "이 책은 정말 재밌어요!")
    private String content;
    @Schema(description = "리뷰 감정 태그", example = "HAPPINESS")
    private String tag;
    @Schema(description = "좋아요 수", example = "0")
    private Long likeCount;
    @Schema(description = "유저 레벨", example = "1")
    private Integer level;
    @Schema(description = "리뷰 생성일", example = "2021-08-01T00:00:00")
    private String createdAt;
    @Schema(description = "리뷰 수정일", example = "2021-08-01T00:00:00")
    private String updatedAt;

    @Builder
    public ReviewInfoResponse(Long bookId, Long reviewId, String userNickname, String content, TagName tag, Long likeCount, Integer level, LocalDateTime createdAt, LocalDateTime updatedAt,String url) {
        this.bookId = bookId;
        this.reviewId = reviewId;
        this.userNickname = userNickname;
        this.content = content;
        this.tag = tag.toString();
        this.likeCount = likeCount;
        this.level = level;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toString() : null;
        this.userProfileImage = url;
    }
}
