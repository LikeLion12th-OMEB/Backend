package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.api.BookApplicationControllerApi;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.usecase.BookUseCase;
import com.example.OMEB.domain.book.presentation.dto.request.BookApplicationRequest;
import com.example.OMEB.domain.book.presentation.dto.request.BookSearchRequest;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.NaverBookListResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BookApplicationController implements BookApplicationControllerApi {

    private final BookUseCase bookUseCase;

    @PutMapping("/v1/books/application")
    public ResponseEntity<ResponseBody<NaverBookListResponse>> serachBooks(@RequestBody @Valid BookSearchRequest bookSearchRequest) { // TODO : 인증 필요 없는지 확인 필요
        log.info("[BookApplicationController] (serachBooks) search books request: {}", bookSearchRequest);
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.searchTitleBooks(bookSearchRequest)));
    }

    @PostMapping("/v1/book/application")
    public ResponseEntity<ResponseBody<Void>> applicationBook(@RequestBody @Valid BookApplicationRequest bookApplicationRequest) {
        bookUseCase.applicationBook(bookApplicationRequest);
        return ResponseEntity.ok(createSuccessResponse());
    }


}
