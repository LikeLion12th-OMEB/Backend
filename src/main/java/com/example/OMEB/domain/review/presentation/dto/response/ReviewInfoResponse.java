package com.example.OMEB.domain.review.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfoResponse {
    private Long bookId;
    private Long reviewId;
    private String userNickname;
    private String content;
    private String tag;
    private Long likeCount;
    private Integer level;
    private String createdAt;
    private String updatedAt;

    @Builder
    public ReviewInfoResponse(Long bookId, Long reviewId, String userNickname, String content, String tag, Long likeCount, Integer level, String createdAt, String updatedAt) {
        this.bookId = bookId;
        this.reviewId = reviewId;
        this.userNickname = userNickname;
        this.content = content;
        this.tag = tag;
        this.likeCount = likeCount;
        this.level = level;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
