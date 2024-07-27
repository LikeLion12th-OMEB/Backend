package com.example.OMEB.global.config;

import com.example.OMEB.global.jwt.JwtFilter;
import com.example.OMEB.global.jwt.JwtUtils;
import com.example.OMEB.global.oauth.HttpCookiesOAuth2AuthorizationRequestRepository;
import com.example.OMEB.global.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.example.OMEB.global.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.OMEB.global.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final HttpCookiesOAuth2AuthorizationRequestRepository httpCookiesOAuth2AuthorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtUtils jwtUtils;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .rememberMe(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)

                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers().permitAll()
                        .anyRequest().permitAll())

                .addFilterBefore(new JwtFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)

                .oauth2Login((configure) -> configure
                        .authorizationEndpoint((config) -> config.authorizationRequestRepository(httpCookiesOAuth2AuthorizationRequestRepository))
                        .userInfoEndpoint((config) -> config.userService(customOAuth2UserService))
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler))
        ;

        return http.build();
    }
}
