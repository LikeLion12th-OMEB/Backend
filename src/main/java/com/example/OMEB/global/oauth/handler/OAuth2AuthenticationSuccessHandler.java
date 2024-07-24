package com.example.OMEB.global.oauth.handler;

import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.JwtUtils;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository.redirectUriCookieName;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository;
    private boolean isLogin;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // principal 객체 꺼내기 -> providerId를 가지고 회원가입 여부 체크 및 save
        // 액세스 토큰 및 리프레시 토큰 제작 후 save
        // 쿠키 가지고 리다이렉트 uri 설정 -> 액세스, 리프레시 토큰, 최초로그인 여부를 가지고 리다이렉트 요청 보내기

//        OAuth2UserPrincipal principal =
//                (authentication.getPrincipal() instanceof OAuth2UserPrincipal)
//                ? (OAuth2UserPrincipal) authentication.getPrincipal()
//                : null;
        OAuth2UserPrincipal principal;
        Object temp = authentication.getPrincipal();

        if (temp instanceof OAuth2UserPrincipal) {
            principal = (OAuth2UserPrincipal) temp;
        }else{
            principal = null;
        }


        String providerId = principal.getUserInfo().getProviderId();

        isLogin = true;
        User user = userRepository.findByProviderId(providerId)
                .orElseGet(() -> {
                    return signUp(providerId, principal.getUserInfo().getProvider());
                });

        String accessToken = jwtUtils.createAccessToken(user.getId());
        String refreshToken = jwtUtils.createRefreshToken(user.getId());
        // TODO : Redis에 refreshToken save

        Cookie redirectCookie = CookieUtils.getCookie(request, redirectUriCookieName);
        if (redirectCookie == null){
            throw new ServiceException(ErrorCode.NOT_FOUND_COOKIE);
        }
        String redirectUri = UriComponentsBuilder.fromUriString(redirectCookie.getValue())
                .queryParam("isLogin", isLogin)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        super.clearAuthenticationAttributes(request);
        httpCookiesOAuth2AuthorizationRequestRepository.removeCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
    public User signUp(String providerId, OAuth2Provider provider){
        User user = new User(provider, providerId);
        isLogin = false;
        return userRepository.save(user);
        // TODO : Redis repository
    }
}
