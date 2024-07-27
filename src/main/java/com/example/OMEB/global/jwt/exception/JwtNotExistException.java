package com.example.OMEB.global.jwt.exception;

import com.example.OMEB.global.base.exception.ErrorCode;

public class JwtNotExistException extends CustomJwtException{
    public JwtNotExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
