package com.example.OMEB.global.jwt.exception;

import com.example.OMEB.global.base.exception.ErrorCode;


public class JwtVerifyException extends CustomJwtException {
    public JwtVerifyException(ErrorCode errorCode) {
        super(errorCode);
    }
}