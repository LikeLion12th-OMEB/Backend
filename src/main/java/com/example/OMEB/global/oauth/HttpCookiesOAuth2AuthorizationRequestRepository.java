package com.example.OMEB.global.oauth;

import com.example.OMEB.global.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class HttpCookiesOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    // 로그인 페이지 요청 uri 예시
    // http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000
    public final static String authorizationRequestCookieName = "authorization_request_cookie";
    public final static String redirectUriCookieName = "redirect_uri";
    public final int COOKIE_EXPIRE_SECONDS = 180;
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        return getAuthorizationRequest(request);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        if (authorizationRequest == null) {
            this.removeAuthorizationRequest(request, response);
        } else {
            CookieUtils.createCookie(response,
                    authorizationRequestCookieName,
                    CookieUtils.ObjectToString(authorizationRequest),
                    COOKIE_EXPIRE_SECONDS);
            CookieUtils.createCookie(response,
                    redirectUriCookieName,
                    request.getParameter(redirectUriCookieName),
                    COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(response, "response cannot be null");
        OAuth2AuthorizationRequest authorizationRequest = this.loadAuthorizationRequest(request);

        return authorizationRequest;
    }

    public void removeCookies(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse){
        CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, authorizationRequestCookieName);
        CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, redirectUriCookieName);
    }
    private OAuth2AuthorizationRequest getAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.StringToObject(
                CookieUtils.getCookie(request, authorizationRequestCookieName).getValue(),
                OAuth2AuthorizationRequest.class
        );
    }

}
