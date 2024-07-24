package com.example.OMEB.global.oauth.user.naver;

import com.example.OMEB.global.oauth.user.AbstractOAuth2UserInfo;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import com.example.OMEB.global.oauth.user.OAuth2UserInfo;

import java.util.Map;

public class NaverUserInfo extends AbstractOAuth2UserInfo {
    public NaverUserInfo(Map<String, Object> attributes) {
        AbstractOAuth2UserInfo((Map<String, Object>)attributes.get("response"));
    }

    @Override
    protected void AbstractOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.providerId = attributes.get("id").toString();
        this.provider = OAuth2Provider.NAVER;
    }
}
