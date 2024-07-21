package com.example.OMEB.global.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_0001", "잘못된 입력 값입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "COMMON_0002", "잘못된 타입입니다."),
    MISSING_INPUT_VALUE(HttpStatus.BAD_REQUEST,"COMMON_0003", "인자가 부족합니다."),
    NOT_EXIST_API(HttpStatus.BAD_REQUEST, "COMMON_0004", "요청 주소가 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_0005", "사용할 수 없는 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_0006", "서버 에러입니다."),
    INVALID_JSON(HttpStatus.BAD_REQUEST, "COMMON_0007", "JSON 파싱 오류입니다."),
    INVALID_INPUT_TAG(HttpStatus.BAD_REQUEST, "COMMON_0008", "감정 태그 입력이 잘못되었습니다."),


    // User
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_0001", "사용자를 찾을 수 없습니다."),

    // Book
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "BOOK_0001", "책을 찾을 수 없습니다."),
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "TAG_0001", "태그를 찾을 수 없습니다."),


    // Review
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "REVIEW_0001", "리뷰를 찾을 수 없습니다."),
    REVIEW_NOT_MATCH_USER(HttpStatus.CONFLICT, "REVIEW_002","해당 사용자는 해당 리뷰에 대한 권한이 없습니다." ),
    ALREADY_LIKE_REVIEW(HttpStatus.CONFLICT, "REVIEW_003", "이미 좋아요를 누른 리뷰입니다."),









    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
