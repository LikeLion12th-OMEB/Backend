package com.example.OMEB.domain.user.presentation.dto.response.rank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserRankInfoResponse", description = "랭킹 페이지 유저 정보 응답")
public class UserRankInfoResponse {
    @Schema(description = "유저 DB id", example = "1")
    private Long id;
    @Schema(description = "유저 닉네임", example = "멋쟁이사자")
    private String nickname;
    @Schema(description = "사용자 레벨", example = "2")
    private Integer level;
    @Schema(description = "사용자 경험치", example = "20")
    private Integer exp;
    @Schema(description = "사용자 프로밀 사진", example = "https://omeb-image.s3.ap-northeast-2.amazonaws.com/default_profile.png")
    private String profileImageUrl;
}
