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

    // Auth
    NOT_FOUND_COOKIE(HttpStatus.NOT_FOUND, "AUTH_0001", "쿠키를 찾을 수 없습니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_0002", "만료된 Jwt 토큰입니다."),
    JWT_AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "AUTH_0003", "Jwt 토큰 인증이 실패했습니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "AUTH_0004", "인증되지 않은 사용자입니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "AUTH_0005", "잘못된 접근입니다."),


    // User
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_0001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_INFO(HttpStatus.BAD_REQUEST, "USER_0002", "중복된 유저 정보입니다."),

    // Book
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "BOOK_0001", "책을 찾을 수 없습니다."),
    APPLICATION_NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "BOOK_0002", "해당 책 제목에 대한 검색 결과가 없습니다."),
    APPLICATION_TOO_MANY_BOOKS(HttpStatus.BAD_REQUEST,"BOOK_0003" , "검색 결과가 너무 많습니다. 검색어를 더 구체적으로 입력해주세요."),
    APPLICATION_ALREADY_EXIST_BOOK(HttpStatus.CONFLICT, "BOOK_0004", "이미 등록된 책입니다."),
    APPLICATION_ALREADY_EXIST_BOOKMARK(HttpStatus.CONFLICT, "BOOK_0005", "해당 책은 이미 북마크 되어있습니다."),
    NOT_FOUND_BOOKMARK(HttpStatus.NOT_FOUND, "Book_0006", "해당 책은 북마크 되어있지 않습니다."),


    // Tag
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "TAG_0001", "태그를 찾을 수 없습니다."),

    // Review
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "REVIEW_0001", "리뷰를 찾을 수 없습니다."),
    REVIEW_NOT_MATCH_USER(HttpStatus.CONFLICT, "REVIEW_0002","해당 사용자는 해당 리뷰에 대한 권한이 없습니다." ),
    ALREADY_LIKE_REVIEW(HttpStatus.CONFLICT, "REVIEW_0003", "이미 좋아요를 누른 리뷰입니다."),

    // File
    NOT_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "FILE_0001", "파일 확장자가 존재하지 않습니다."),
    NOT_IMAGE_FILE(HttpStatus.BAD_REQUEST, "FILE_0002", "이미지 파일이 아닙니다."),
    INVALID_S3_URL(HttpStatus.BAD_REQUEST, "FILE_0003", "S3 URL이 올바르지 않습니다."),
    INVALID_FILE_USER(HttpStatus.UNAUTHORIZED, "FILE_0004", "해당 파일은 사용자의 파일이 아닙니다."),


    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
