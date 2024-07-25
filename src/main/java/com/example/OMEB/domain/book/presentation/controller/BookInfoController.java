package com.example.OMEB.domain.book.presentation.controller;

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

@Tag(name = "Book API", description = "책 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookInfoController {

    private final BookUseCase bookUseCase;

    @Operation(summary = "책 상세 조회 API", description = "책의 상세 정보를 조회하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = BookInfoResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/v1/book/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@Schema(hidden = true) Long userId, @PathVariable @Schema(description = "책 id" , example = "1") Long bookId) {
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(userId,bookId)));
    }
}
