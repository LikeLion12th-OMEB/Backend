package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserInfoResponse", description = "마이페이지 유저 정보 응답")
public class UserInfoResponse {
    @Schema(description = "유저 DB id", example = "1")
    private Long id;
    @Schema(description = "유저 닉네임", example = "멋쟁이사자")
    private String nickname;
    @Schema(description = "OAuth 플랫폼", example = "GOOGLE")
    private OAuth2Provider provider;
    @Schema(description = "사용자 레벨", example = "2")
    private Integer level;
    @Schema(description = "사용자 경험치", example = "20")
    private Integer exp;
    @Schema(description = "사용자 프로밀 사진", example = "https://omeb-image.s3.ap-northeast-2.amazonaws.com/default_profile.png")
    private String profileImageUrl;
    @Schema(description = "사용자 마지막 로그인 날짜", example = "2021-08-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate lastLoginAt;

    public static UserInfoResponse entityToResponse(User user){
        return UserInfoResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .level(user.getLevel())
                .exp(user.getExp())
                .profileImageUrl(user.getProfileImageUrl())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
