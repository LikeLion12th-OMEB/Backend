package com.example.OMEB.global.base.dto;

import lombok.Getter;

@Getter
public final class SuccessResponseBody<T> extends ResponseBody<T> {
    private final T data;

    public SuccessResponseBody() {
        data = null;
    }

    public SuccessResponseBody(T result) {
        this.data = result;
    }

    public static ResponseBody<Void> createSuccessResponse() {
        return new SuccessResponseBody<>();
    }

    public static <T> ResponseBody<T> createSuccessResponse(T data) {
        return new SuccessResponseBody<>(data);
    }
}
