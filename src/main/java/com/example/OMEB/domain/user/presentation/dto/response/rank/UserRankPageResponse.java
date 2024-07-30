package com.example.OMEB.domain.user.presentation.dto.response.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserRankPageResponse", description = "랭킹 페이지 응답")
public class UserRankPageResponse {
    @Schema(description = "페이지 오프셋", example = "1")
    private int pageOffset;
    @Schema(description = "페이지 사이즈", example = "10")
    private int pageSize;
    @Schema(description = "총 페이지 수", example = "10")
    private int totalPage;
    @Schema(description = "유저 랭킹 리스트")
    private List<UserRankInfoResponse> userRankInfoResponseList;

    public static UserRankPageResponse pageToResponse(Page<UserRankInfoResponse> userRankInfoResponsePage){
        return UserRankPageResponse.builder()
                .pageOffset(userRankInfoResponsePage.getNumber() + 1)
                .pageSize(userRankInfoResponsePage.getSize())
                .totalPage(userRankInfoResponsePage.getTotalPages())
                .userRankInfoResponseList(userRankInfoResponsePage.getContent())
                .build();
    }
}
