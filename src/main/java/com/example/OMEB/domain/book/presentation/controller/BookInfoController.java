package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.api.BookInfoControllerApi;
import com.example.OMEB.domain.book.application.usecase.BookUseCase;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.book.presentation.dto.response.EmotionBookTitleInfoListResponse;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BookInfoController implements BookInfoControllerApi {

    private final BookUseCase bookUseCase;

    @PreAuthorize("permitAll()")
    @GetMapping("/v2/book/{bookId}")
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@UserPrincipal CustomUserPrincipal userPrincipal, @PathVariable @Schema(description = "책 id" , example = "1") Long bookId) {
        log.info("[BookInfoController] (getBook) get book request: {}", bookId);
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(userPrincipal,bookId)));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/v1/book/review-rank")
    public ResponseEntity<ResponseBody<BookTitleListResponse>> getBookReviewRank() {
        log.info("[BookInfoController] (getBookReviewRank) get book review rank request");
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBookReviewRank()));
    }

    @PreAuthorize("permitAll()") //TODO : 지금은 emotion을 30개 데이터 전부를 반환하도록 했지만 의논해보고 수정 해야함 그리고 무조건 순위대로 나오게할지도 고민
    @GetMapping("/v1/book/emotion")
    public ResponseEntity<ResponseBody<EmotionBookTitleInfoListResponse>> getEmotionRank(@RequestParam("emotion") @Schema(description = "감정", example = "ANGER") TagName emotion) {
        log.info("[BookInfoController] (getEmotionRank) get emotion rank request");
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getEmotionRank(emotion)));
    }


}
