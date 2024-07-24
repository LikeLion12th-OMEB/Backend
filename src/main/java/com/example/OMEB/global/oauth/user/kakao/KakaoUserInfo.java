package com.example.OMEB.global.oauth.user.kakao;

import com.example.OMEB.global.oauth.user.AbstractOAuth2UserInfo;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import com.example.OMEB.global.oauth.user.OAuth2UserInfo;

import java.util.Map;

public class KakaoUserInfo extends AbstractOAuth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes){
        AbstractOAuth2UserInfo(attributes);
    }

    @Override
    protected void AbstractOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.providerId = attributes.get("id").toString();
        this.provider = OAuth2Provider.KAKAO;
    }
}
