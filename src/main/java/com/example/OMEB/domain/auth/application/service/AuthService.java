package com.example.OMEB.domain.auth.application.service;

import com.example.OMEB.domain.auth.presentation.dto.request.TokenRequest;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.domain.user.application.IncreaseExpType;
import com.example.OMEB.domain.user.application.service.UserService;
import com.example.OMEB.domain.user.persistence.entity.ExpLog;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.ExpLogRepository;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.JwtUtils;
import com.example.OMEB.global.jwt.refreshToken.RefreshToken;
import com.example.OMEB.global.jwt.refreshToken.RefreshTokenRepository;
import com.example.OMEB.global.utils.CookieUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.OMEB.global.oauth.handler.OAuth2AuthenticationSuccessHandler.USER_COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ExpLogRepository expLogRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public SignUpResponse createUser(HttpServletRequest request,
                                     HttpServletResponse response,
                                     String nickname){
        Cookie cookie = CookieUtils.getCookie(request, USER_COOKIE_NAME);
        if (cookie == null)
            throw new ServiceException(ErrorCode.NOT_FOUND_COOKIE);
        User user = CookieUtils.StringToObject(cookie.getValue(), User.class);
        user.setNickname(nickname);
        user.updateLastLoginAt();
        userRepository.save(user);
        userRepository.flush();
        userService.increaseExp(user, IncreaseExpType.DAY_LOGIN);

        CookieUtils.deleteCookie(request, response, USER_COOKIE_NAME);
        String accessToken = jwtUtils.createAccessToken(user.getId());
        String refreshToken = jwtUtils.createRefreshToken(user.getId());
        refreshTokenRepository.save(new RefreshToken(refreshToken, user.getId()));

        return SignUpResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse reissueToken(TokenRequest request){
        RefreshToken redisRefreshToken = refreshTokenRepository.findById(request.getRefreshToken())
                .orElseThrow(() -> new ServiceException(ErrorCode.JWT_EXPIRED_TOKEN));
        if (!redisRefreshToken.getRefreshToken().equals(request.getRefreshToken())){
            logout(redisRefreshToken.getRefreshToken());
            throw new ServiceException(ErrorCode.INVALID_ACCESS);
        }

        jwtUtils.validateToken(request.getRefreshToken());
        try{
            jwtUtils.validateToken(request.getAccessToken());
        } catch (ExpiredJwtException e){
            String accessToken = jwtUtils.createAccessToken(redisRefreshToken.getUserId());
            String refreshToken = jwtUtils.createRefreshToken(redisRefreshToken.getUserId());
            refreshTokenRepository.save(new RefreshToken(refreshToken, redisRefreshToken.getUserId()));

            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        // AccessToken이 유효하지 않거나 만료되지 않았음
        logout(redisRefreshToken.getRefreshToken());
        throw new ServiceException(ErrorCode.INVALID_ACCESS);
    }

    public void logout(String refreshToken){
        refreshTokenRepository.findById(refreshToken).ifPresent(refreshTokenRepository::delete);
    }

    public void checkNicknameDuplicate(String nickname){
        if(userRepository.findByNickname(nickname).isPresent()){
            throw new ServiceException(ErrorCode.DUPLICATE_INFO);
        }
    }
}
