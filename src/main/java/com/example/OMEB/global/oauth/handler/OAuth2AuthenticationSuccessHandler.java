package com.example.OMEB.global.oauth.handler;

import com.example.OMEB.domain.user.application.IncreaseExpType;
import com.example.OMEB.domain.user.application.service.UserService;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.jwt.JwtUtils;
import com.example.OMEB.global.jwt.refreshToken.RefreshToken;
import com.example.OMEB.global.jwt.refreshToken.RefreshTokenRepository;
import com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import com.example.OMEB.global.oauth.user.OAuth2UserPrincipal;
import com.example.OMEB.global.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;

import static com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository.redirectUriCookieName;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;
    private boolean isLogin;
    public final static String USER_COOKIE_NAME = "user";
    public final int COOKIE_EXPIRE_SECONDS = 300;
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // principal 객체 꺼내기 -> providerId를 가지고 회원가입 여부 체크 및 save
        // 액세스 토큰 및 리프레시 토큰 제작 후 save
        // 쿠키 가지고 리다이렉트 uri 설정 -> 액세스, 리프레시 토큰, 최초로그인 여부를 가지고 리다이렉트 요청 보내기

        OAuth2UserPrincipal principal;
        Object temp = authentication.getPrincipal();

        if (temp instanceof OAuth2UserPrincipal) {
            principal = (OAuth2UserPrincipal) temp;
        }else{
            principal = null;
        }

        String providerId = principal.getUserInfo().getProviderId();
        Cookie redirectCookie = CookieUtils.getCookie(request, redirectUriCookieName);

        isLogin = true;
        User user = userRepository.findByProviderId(providerId);
        String redirectUri;
        if (user != null){
            String accessToken = jwtUtils.createAccessToken(user.getId());
            String refreshToken = jwtUtils.createRefreshToken(user.getId());
            refreshTokenRepository.save(new RefreshToken(refreshToken, user.getId()));

            redirectUri = UriComponentsBuilder.fromUriString(redirectCookie.getValue())
                    .queryParam("isLogin", isLogin)
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();

            LocalDate lastLoginDate = user.getLastLoginAt();
            user.updateLastLoginAt();
            if(lastLoginDate.isBefore(LocalDate.now())){
                userService.increaseExp(user, IncreaseExpType.DAY_LOGIN);
            }
        } else{
            signUp(providerId, principal.getUserInfo().getProvider(), response);
            redirectUri = UriComponentsBuilder.fromUriString(redirectCookie.getValue())
                    .queryParam("isLogin", isLogin)
                    .build().toUriString();
        }
        super.clearAuthenticationAttributes(request);
        httpCookiesOAuth2AuthorizationRequestRepository.removeCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
    public void signUp(String providerId, OAuth2Provider provider,
                       HttpServletResponse response){
        User user = new User(provider, providerId);
        //TODO : 수정
        user.updateProfileImageUrl("https://omeb-image.s3.ap-northeast-2.amazonaws.com/default_profile.png");
        isLogin = false;

        CookieUtils.createCookie(response,
                USER_COOKIE_NAME,
                CookieUtils.ObjectToString(user),
                COOKIE_EXPIRE_SECONDS);
    }
}