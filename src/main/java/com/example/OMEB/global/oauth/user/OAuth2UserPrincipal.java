package com.example.OMEB.global.oauth.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class OAuth2UserPrincipal implements OAuth2User, UserDetails {
    @Getter
    private final OAuth2UserInfo userInfo;

    public OAuth2UserPrincipal(OAuth2UserInfo oauth2UserInfo) {
        this.userInfo = oauth2UserInfo;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userInfo.getProviderId();
    }

    @Override
    public String getName() {
        return userInfo.getProviderId();
    }
}
