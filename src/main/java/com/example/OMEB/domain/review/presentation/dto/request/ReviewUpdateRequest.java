package com.example.OMEB.domain.review.presentation.dto.request;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "리뷰 수정 요청")
public class ReviewUpdateRequest {
    @Schema(description = "수정할 Review 내용", example = "이 책은 정말 재밌어요!")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    @Schema(description = "수정할 Review 감정태그", example = "HAPPINESS")
    @NotNull(message = "태그는 필수입니다.")
    private TagName tag;
}
