package com.example.OMEB.domain.bookmark.presentation.controller;

import com.example.OMEB.domain.bookmark.api.BookMarkControllerApi;
import com.example.OMEB.domain.bookmark.application.service.BookMarkServiceImpl;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.bookmark.presentation.dto.response.BookMarkBookTitleListResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookMarkController implements BookMarkControllerApi {
    private final BookMarkServiceImpl bookMarkService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/v1/bookmark/{bookId}")
    public ResponseEntity<ResponseBody<Void>> saveBookMark(@UserPrincipal CustomUserPrincipal userPrincipal , @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.saveBookMark(userPrincipal.userId(), bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/v1/bookmark")
    public ResponseEntity<ResponseBody<BookMarkBookTitleListResponse>> getBookMark(@UserPrincipal CustomUserPrincipal userPrincipal) { // TODO : 페이징 필요한지 확인
        return ResponseEntity.ok(createSuccessResponse(bookMarkService.findUserBookMark(userPrincipal.userId())));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/v1/bookmark/{bookId}")
    public ResponseEntity<ResponseBody<Void>> deleteBookMark(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                             @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.deleteBookMark(userPrincipal.userId(), bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
