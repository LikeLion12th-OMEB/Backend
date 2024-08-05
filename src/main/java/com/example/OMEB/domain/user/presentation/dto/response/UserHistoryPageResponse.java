package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.review.presentation.dto.response.UserReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserHistoryPageResponse", description = "사용자 히스토리 페이지 응답")
public class UserHistoryPageResponse {
    @Schema(description = "페이지 오프셋", example = "1")
    private int pageOffset;
    @Schema(description = "페이지 사이즈", example = "10")
    private int pageSize;
    @Schema(description = "총 페이지 수", example = "10")
    private int totalPage;
    @Schema(description = "히스토리 정보 리스트")
    private List<HistoryResponse> historyResponseList;
}
