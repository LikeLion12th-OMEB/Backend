package com.example.OMEB.global.oauth.user;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> getAttributes();
    String getProviderId();
    OAuth2Provider getProvider();
}
