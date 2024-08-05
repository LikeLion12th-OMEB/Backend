package com.example.OMEB.domain.user.presentation.controller;

import com.amazonaws.Response;
import com.example.OMEB.domain.user.application.service.UserService;
import com.example.OMEB.domain.user.presentation.api.UserApi;
import com.example.OMEB.domain.user.presentation.dto.request.HistoryRequest;
import com.example.OMEB.domain.user.presentation.dto.request.UpdateUserInfoRequest;
import com.example.OMEB.domain.user.presentation.dto.response.HistoryResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserExpLogResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserHistoryPageResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserInfoResponse;
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



    @GetMapping("/my-explogs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<UserExpLogResponse>>> getUserExpLogs(@UserPrincipal CustomUserPrincipal userPrincipal){
        return ResponseEntity.ok(createSuccessResponse(userService.getUserExpLogs(userPrincipal.userId())));
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

    @PostMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> createHistory(
            @UserPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody HistoryRequest historyRequest){
        userService.createHistory(userPrincipal.userId(), historyRequest.getText(), historyRequest.getBookId());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<UserHistoryPageResponse>> getHistory(
            @UserPrincipal CustomUserPrincipal userPrincipal,
            @RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 넘버(가장 작은 수 1)", example = "1") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "한 페이지의 조회 될 책 수",example = "10") int size,
            @RequestParam(defaultValue = "DESC") @Schema(description = "정렬 방법" , example = "DESC") String sortDirection,
            @RequestParam(defaultValue = "createdAt") @Schema(description = "정렬 기준",example = "createdAt") String sortBy){
        return ResponseEntity.ok(createSuccessResponse(userService.getHistory(userPrincipal.userId(), page-1, size, sortDirection, sortBy)));
    }
}
