package com.example.OMEB.global.oauth.user.google;

import com.example.OMEB.global.oauth.user.AbstractOAuth2UserInfo;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import com.example.OMEB.global.oauth.user.OAuth2UserInfo;

import java.util.Map;

public class GoogleUserInfo extends AbstractOAuth2UserInfo {

    public GoogleUserInfo(Map<String, Object> attributes){
        AbstractOAuth2UserInfo(attributes);
    }

    @Override
    protected void AbstractOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.providerId = attributes.get("sub").toString();
        this.provider = OAuth2Provider.GOOGLE;
    }
}
