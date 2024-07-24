package com.example.OMEB.global.oauth.service;

import com.example.OMEB.global.oauth.user.OAuth2UserInfo;
import com.example.OMEB.global.oauth.user.OAuth2UserPrincipal;
import com.example.OMEB.global.oauth.user.google.GoogleUserInfo;
import com.example.OMEB.global.oauth.user.kakao.KakaoUserInfo;
import com.example.OMEB.global.oauth.user.naver.NaverUserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       OAuth2User oAuth2User = super.loadUser(userRequest);

       String registrationId = userRequest.getClientRegistration().getRegistrationId();
       OAuth2UserInfo userinfo;
       switch (registrationId){
           case "google" -> userinfo = new GoogleUserInfo(oAuth2User.getAttributes());
           case "kakao" -> userinfo = new KakaoUserInfo(oAuth2User.getAttributes());
           case "naver" -> userinfo = new NaverUserInfo(oAuth2User.getAttributes());
           default -> throw new IllegalArgumentException("지원하지 않는 로그인 공급자: " + registrationId);
       }
        return new OAuth2UserPrincipal(userinfo);
    }
}
