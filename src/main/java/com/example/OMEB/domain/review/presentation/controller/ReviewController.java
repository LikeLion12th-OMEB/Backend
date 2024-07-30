package com.example.OMEB.domain.review.presentation.controller;

import com.example.OMEB.domain.review.api.ReviewControllerApi;
import com.example.OMEB.domain.review.application.service.ReviewService;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewUpdateRequest;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewPageResponse;
import com.example.OMEB.domain.review.presentation.dto.response.UserReviewResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController implements ReviewControllerApi {
    private final ReviewService reviewService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/v1/review/{bookId}")
    public ResponseEntity<ResponseBody<ReviewInfoResponse>> createReview(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                                         @PathVariable @Schema(description = "책 id", example = "1") Long bookId,
                                                                         @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.ok(createSuccessResponse(reviewService.createReview(userPrincipal.userId(), bookId, reviewCreateRequest)));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/v1/review/{reviewId}")
    public ResponseEntity<ResponseBody<ReviewInfoResponse>> updateReview(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                                         @PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId,
                                                                         @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        return ResponseEntity.ok(createSuccessResponse(reviewService.updateReview(userPrincipal.userId(), reviewId, reviewUpdateRequest)));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/v1/reviews/{bookId}")
    public ResponseEntity<ResponseBody<ReviewPageResponse>> getReviews(@PathVariable @Schema(description = "책 id", example = "1") Long bookId,
                                                                       @RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 넘버(가장 작은 수 1)", example = "1") int page,
                                                                       @RequestParam(defaultValue = "10") @Schema(description = "한 페이지의 조회 될 책 수",example = "10") int size,
                                                                       @RequestParam(defaultValue = "DESC") @Schema(description = "정렬 방법" , example = "DESC") String sortDirection,
                                                                       @RequestParam(defaultValue = "createdAt") @Schema(description = "정렬 기준",example = "createdAt") String sortBy) {
        return ResponseEntity.ok(createSuccessResponse(reviewService.getReviews(bookId, page-1, size, sortDirection, sortBy)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/v1/review/{reviewId}/like")
    public ResponseEntity<ResponseBody<Void>> likeReview(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                         @PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId) {
        reviewService.likeReview(userPrincipal.userId(),reviewId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/v1/review/{reviewId}")
    public ResponseEntity<ResponseBody<Void>> deleteReview(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                           @PathVariable @Schema(description = "리뷰 id", example = "1")Long reviewId) {
        reviewService.deleteReview(userPrincipal.userId(),reviewId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<UserReviewResponse>>> getUserReviews(@UserPrincipal CustomUserPrincipal userPrincipal){
        return ResponseEntity.ok(createSuccessResponse(reviewService.getUserReviews(userPrincipal.userId())));
    }
}
