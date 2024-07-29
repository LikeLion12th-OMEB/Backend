package com.example.OMEB.domain.auth.presentation.controller;

import com.example.OMEB.domain.auth.presentation.api.TemporaryAuthApi;
import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.JwtUtils;
import com.example.OMEB.global.jwt.refreshToken.RefreshToken;
import com.example.OMEB.global.jwt.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/test")
public class TemporaryAuthController implements TemporaryAuthApi {
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @GetMapping("/jwt-token")
    public ResponseEntity<ResponseBody<TokenResponse>> getToken(){
        User user = userRepository.findById(1L)
                .orElseThrow(()-> new ServiceException(ErrorCode.NOT_FOUND_USER));

        String accessToken = jwtUtils.createAccessToken(1L);
        String refreshToken = jwtUtils.createRefreshToken(1L);
        refreshTokenRepository.save(new RefreshToken(refreshToken, 1L));

        return ResponseEntity.ok(createSuccessResponse(TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build()));
    }
}
