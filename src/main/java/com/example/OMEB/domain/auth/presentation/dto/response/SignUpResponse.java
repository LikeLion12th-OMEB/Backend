package com.example.OMEB.domain.auth.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponse {
    private Long userId;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    @Builder
    public SignUpResponse(Long userId, String nickname, String accessToken, String refreshToken) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
