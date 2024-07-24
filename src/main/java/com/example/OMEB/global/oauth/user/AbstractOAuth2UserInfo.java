package com.example.OMEB.global.oauth.user;

import java.util.Map;

public abstract class AbstractOAuth2UserInfo implements OAuth2UserInfo{
    protected Map<String, Object> attributes;
    protected String providerId;
    protected OAuth2Provider provider;

    protected abstract void AbstractOAuth2UserInfo(Map<String, Object> attributes);

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public OAuth2Provider getProvider() {
        return provider;
    }
}
