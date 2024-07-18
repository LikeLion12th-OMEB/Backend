package com.example.OMEB.global.base.dto;

import com.example.OMEB.global.base.exception.ErrorCode;
import lombok.Getter;

@Getter
public final class FailedResponseBody extends ResponseBody<Void> {
    private final String msg;

    public FailedResponseBody(String code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }

    public static ResponseBody<Void> createFailureResponse(ErrorCode errorCode) {
        return new FailedResponseBody(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }

    public static ResponseBody<Void> createFailureResponse(ErrorCode errorCode, String customMessage) {
        return new FailedResponseBody(
                errorCode.getCode(),
                customMessage
        );
    }
}
