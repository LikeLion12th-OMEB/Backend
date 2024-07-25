package com.example.OMEB.domain.book.presentation;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@Tag(name = " Book Application API", description = "책 신청 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookApplicationController {

    private final BookUseCase bookUseCase;

    @Operation(summary = "신청 가능 책 검색 API", description = "신청 가능한 책을 검색하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = NaverBookListResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "BOOK_0002", description = "해당 책 제목에 대한 검색 결과가 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "BOOK_0003", description = "검색 결과가 너무 많습니다. 검색어를 더 구체적으로 입력해주세요.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0006", description = "서버 에러입니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/v1/books/application")
    public ResponseEntity<ResponseBody<NaverBookListResponse>> serachBooks(@ModelAttribute @Valid BookSearchRequest bookSearchRequest) { // TODO : 인증 필요 없는지 확인 필요
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.searchTitleBooks(bookSearchRequest)));
    }

    @Operation(summary = "책 신청 API", description = "책을 신청하여 서버 db 에 저장하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "BOOK_0004", description = "이미 등록된 책입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "BOOK_0002", description = "해당 책 제목에 대한 검색 결과가 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0006", description = "서버 에러입니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/v1/book")
    public ResponseEntity<ResponseBody<Void>> applicationBook(@RequestBody @Valid BookApplicationRequest bookApplicationRequest) {
        bookUseCase.applicationBook(bookApplicationRequest);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @Operation(summary = "책 상세 조회 API", description = "책의 상세 정보를 조회하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = BookInfoResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/v1/book/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(Long userId,@PathVariable @Schema(description = "책 id" , example = "1") Long bookId) {
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(userId,bookId)));
    }
}
