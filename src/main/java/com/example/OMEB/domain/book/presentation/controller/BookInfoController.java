package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.api.BookInfoControllerApi;
import com.example.OMEB.domain.book.application.usecase.BookUseCase;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BookInfoController implements BookInfoControllerApi {

    private final BookUseCase bookUseCase;

    @GetMapping("/v2/book/{bookId}")
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@UserPrincipal CustomUserPrincipal userPrincipal, @PathVariable @Schema(description = "책 id" , example = "1") Long bookId) {
        log.info("[BookInfoController] (getBook) get book request: {}", bookId);
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(userPrincipal,bookId)));
    }

    @GetMapping("/v1/book/review-rank")
    public ResponseEntity<ResponseBody<BookTitleListResponse>> getBookReviewRank() {
        log.info("[BookInfoController] (getBookReviewRank) get book review rank request");
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBookReviewRank()));
    }


}
