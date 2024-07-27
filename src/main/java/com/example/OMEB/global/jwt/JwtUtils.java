package com.example.OMEB.global.jwt;


import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.jwt.exception.JwtExpiredException;
import com.example.OMEB.global.jwt.exception.JwtVerifyException;
import com.example.OMEB.global.jwt.refreshToken.RefreshToken;
import com.example.OMEB.global.jwt.refreshToken.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.*;

@Component
@PropertySource("classpath:/security/application-security.yml")
public class JwtUtils {
    private final SecretKey secretKey;
    @Value("${jwt.access-token-expiration-time}")
    private String accessTokenExpirationTime;
    @Value("${jwt.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtils(@Value("${jwt.secret-key}") String stringSecretKey, RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
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

        return refreshToken;
    }
    public boolean validateToken(String jwtToken){
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(ErrorCode.JWT_EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new JwtVerifyException(ErrorCode.JWT_AUTHENTICATION_FAILED);
        }
    }

    public Authentication getAuthentication(String jwtToken){
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(jwtToken).getPayload();

        List<? extends SimpleGrantedAuthority> authorities = new ArrayList<>();
        CustomUserPrincipal principal = new CustomUserPrincipal(claims.get("USER_ID", Long.class));
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
