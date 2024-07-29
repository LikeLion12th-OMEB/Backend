package com.example.OMEB.domain.user.presentation.controller;

import com.example.OMEB.domain.user.application.service.UserService;
import com.example.OMEB.domain.user.presentation.api.UserApi;
import com.example.OMEB.domain.user.presentation.dto.request.UpdateUserInfoRequest;
import com.example.OMEB.domain.user.presentation.dto.response.UserBookMarkResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserExpLogResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserReviewResponse;
import com.example.OMEB.domain.user.presentation.dto.response.rank.UserRankPageResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {
    private final UserService userService;
    @GetMapping("/my-page")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<UserInfoResponse>> getUserInfo(@UserPrincipal CustomUserPrincipal userPrincipal) {
        return ResponseEntity.ok(createSuccessResponse(userService.getUserInfo(userPrincipal.userId())));
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<UserReviewResponse>>> getUserReviews(@UserPrincipal CustomUserPrincipal userPrincipal){
        return ResponseEntity.ok(createSuccessResponse(userService.getUserReviews(userPrincipal.userId())));
    }

    @GetMapping("/my-explogs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<UserExpLogResponse>>> getUserExpLogs(@UserPrincipal CustomUserPrincipal userPrincipal){
        return ResponseEntity.ok(createSuccessResponse(userService.getUserExpLogs(userPrincipal.userId())));
    }

    @GetMapping("/my-bookmarks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<UserBookMarkResponse>>> getUserBookMarks(@UserPrincipal CustomUserPrincipal userPrincipal){
        return ResponseEntity.ok(createSuccessResponse(userService.getUserBookMarks(userPrincipal.userId())));
    }

    @PatchMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updateUserInfo(
            @UserPrincipal CustomUserPrincipal userPrincipal, @RequestBody @Valid UpdateUserInfoRequest updateUserInfoRequest) {
        userService.updateUserInfo(userPrincipal.userId(), updateUserInfoRequest.getNickname());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/rank")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseBody<UserRankPageResponse>> getUserRankList(
            @RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 번호", example = "1") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "페이지 당 유저 수",example = "10") int size) {
        return ResponseEntity.ok(createSuccessResponse(userService.getUserRankList(page-1, size)));
    }
}
