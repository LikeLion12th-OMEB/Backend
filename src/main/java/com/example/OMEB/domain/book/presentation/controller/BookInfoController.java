package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.api.BookInfoControllerApi;
import com.example.OMEB.domain.book.application.usecase.BookUseCase;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookInfoController implements BookInfoControllerApi {

    private final BookUseCase bookUseCase;

    @GetMapping("/v1/book/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@Schema(hidden = true) Long userId, @PathVariable @Schema(description = "책 id" , example = "1") Long bookId) {
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(userId,bookId)));
    }
}
