package com.example.OMEB.domain.user.application;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IncreaseExpType {
    WRITE_REVIEW(100, "리뷰 작성"),
    GET_LIKE(20, "리뷰 좋아요 획득"),
    CREATE_LIKE(10, "리뷰 좋아요 추가"),
    DAY_LOGIN(20, "금일 최초 로그인");

    private final int exp;
    private final String content;

}
