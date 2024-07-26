package com.example.OMEB.global.config;

import com.example.OMEB.global.jwt.JwtUtils;
import com.example.OMEB.global.jwt.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final RefreshTokenRepository refreshTokenRepository;
    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret-key}") String stringSecretKey){
        return new JwtUtils(stringSecretKey, refreshTokenRepository);
    }
}
