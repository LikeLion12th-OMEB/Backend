package com.example.OMEB.global.oauth.handler;

import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository;
import com.example.OMEB.global.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository.redirectUriCookieName;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String targetUrl = CookieUtils.getCookie(request, redirectUriCookieName).getValue();
        if (targetUrl == null){
            throw new ServiceException(ErrorCode.NOT_FOUND_COOKIE);
        }

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        httpCookiesOAuth2AuthorizationRequestRepository.removeCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
