package com.example.OMEB.global.jwt.exception;

import com.example.OMEB.global.base.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class CustomJwtException extends JwtException {
    private final ErrorCode errorCode;
    public CustomJwtException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
