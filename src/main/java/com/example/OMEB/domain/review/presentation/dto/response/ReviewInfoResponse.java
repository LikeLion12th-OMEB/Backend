package com.example.OMEB.domain.review.presentation.dto.response;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    public ReviewInfoResponse(Long bookId, Long reviewId, String userNickname, String content, TagName tag, Long likeCount, Integer level, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookId = bookId;
        this.reviewId = reviewId;
        this.userNickname = userNickname;
        this.content = content;
        this.tag = tag.toString();
        this.likeCount = likeCount;
        this.level = level;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toString() : null;
    }
}
