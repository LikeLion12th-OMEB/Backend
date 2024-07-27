package com.example.OMEB.global.jwt.refreshToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 1209600)
@AllArgsConstructor
@Getter
public class RefreshToken {
    @Id
    private String refreshToken;
    private Long userId;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
