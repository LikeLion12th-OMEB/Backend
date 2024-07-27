package com.example.OMEB.global.jwt.exception;

import com.example.OMEB.global.base.exception.ErrorCode;

public class JwtExpiredException extends CustomJwtException {
    public JwtExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
