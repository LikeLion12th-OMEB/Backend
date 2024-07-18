package com.example.OMEB.global.base.exception;


import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
