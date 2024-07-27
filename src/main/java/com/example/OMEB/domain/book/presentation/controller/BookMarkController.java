package com.example.OMEB.domain.book.presentation.controller;

import com.example.OMEB.domain.book.api.BookMarkControllerApi;
import com.example.OMEB.domain.book.application.service.BookMarkServiceImpl;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookMarkController implements BookMarkControllerApi {
    private final BookMarkServiceImpl bookMarkService;

    @PostMapping("/v1/bookmark/{bookId}")
    public ResponseEntity<ResponseBody<Void>> saveBookMark(@UserPrincipal CustomUserPrincipal userPrincipal , @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.saveBookMark(userPrincipal.userId(), bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/v1/bookmark")
    public ResponseEntity<ResponseBody<BookTitleListResponse>> getBookMark(@UserPrincipal CustomUserPrincipal userPrincipal) { // TODO : 페이징 필요한지 확인
        return ResponseEntity.ok(createSuccessResponse(bookMarkService.findUserBookMark(userPrincipal.userId())));
    }

    @DeleteMapping("/v1/bookmark/{bookId}")
    public ResponseEntity<ResponseBody<Void>> deleteBookMark(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                             @PathVariable @Schema(description = "책 id",example = "1") Long bookId) {
        bookMarkService.deleteBookMark(userPrincipal.userId(), bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
