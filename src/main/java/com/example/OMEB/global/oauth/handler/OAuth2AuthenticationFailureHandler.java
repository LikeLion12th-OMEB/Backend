package com.example.OMEB.global.oauth.handler;

import com.example.OMEB.global.base.dto.FailedResponseBody;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository;
import com.example.OMEB.global.utils.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository.redirectUriCookieName;
import static com.example.OMEB.global.utils.CookieUtils.objectMapper;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository;

    public OAuth2AuthenticationFailureHandler(HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository) {
        this.httpCookiesOAuth2AuthorizationRequestRepository = httpCookiesOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String targetUrl = CookieUtils.getCookie(request, redirectUriCookieName).getValue();
        if (targetUrl == null){
            response.setStatus(ErrorCode.NOT_FOUND_COOKIE.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    FailedResponseBody.createFailureResponse(ErrorCode.NOT_FOUND_COOKIE)));
        } else{
            targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", exception.getLocalizedMessage())
                    .build().toUriString();

            httpCookiesOAuth2AuthorizationRequestRepository.removeCookies(request, response);

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }
}
