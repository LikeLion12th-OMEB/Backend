package com.example.OMEB.domain.review.presentation.controller;

import com.example.OMEB.domain.review.application.service.ReviewService;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.base.dto.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @AssignUserId
    @PostMapping("/v1/review/{bookId}")
    public ResponseEntity<ResponseBody<ReviewInfoResponse>> createReview(Long userId, @PathVariable Long bookId, @RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ResponseEntity.ok(createSuccessResponse(reviewService.createReview(userId, bookId, reviewCreateRequest)));
    }

}
