package com.example.OMEB.global.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:/security/application-security.yml")
public class JwtUtils {
    private final SecretKey secretKey;
    @Value("${jwt.access-token-expiration-time}")
    private String accessTokenExpirationTime;
    @Value("${jwt.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;

    public JwtUtils(@Value("${jwt.secret-key}") String stringSecretKey){
        String keyBase64Encoded = Base64.getEncoder().encodeToString(stringSecretKey.getBytes());
        secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String createAccessToken(Long userId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("USER_ID", userId);
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + Long.parseLong(accessTokenExpirationTime) * 1000L))
                .signWith(secretKey)
                .compact();
    }
    public String createRefreshToken(Long userId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("USER_ID", userId);
        Date now = new Date(System.currentTimeMillis());

        String refreshToken = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + Long.parseLong(refreshTokenExpirationTime) * 1000L))
                .signWith(secretKey)
                .compact();

        // TODO : refresh 토큰 저장
        return refreshToken;
    }
}
