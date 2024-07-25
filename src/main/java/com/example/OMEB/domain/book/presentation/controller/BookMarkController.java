package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.application.service.BookMarkServiceImpl;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@Tag(name = " BookMark API", description = "북마크 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookMarkController {
    private final BookMarkServiceImpl bookMarkService;

    @Operation(summary = "BookMark 저장 API", description = "북마크를 저장하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "BOOK_0005", description = "해당 책은 이미 북마크 되어있습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/v1/bookmark/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> saveBookMark(@Schema(hidden = true) Long userId , @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.saveBookMark(userId, bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @Operation(summary = "특정 회원의 북마크 리스트 조회 API", description = "특정 회원의 북마크 리스트를 조회하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = BookTitleListResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/v1/bookmark")
    @AssignUserId
    public ResponseEntity<ResponseBody<BookTitleListResponse>> getBookMark(@Schema(hidden = true) Long userId) { // TODO : 페이징 필요한지 확인
        return ResponseEntity.ok(createSuccessResponse(bookMarkService.findUserBookMark(userId)));
    }

    @Operation(summary = "북마크 해제 API", description = "북마크를 해제하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "Book_0006", description = "해당 책은 북마크 되어있지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/v1/bookmark/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> deleteBookMark(@Schema(hidden = true) Long userId,
                                                             @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.deleteBookMark(userId, bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
