package com.example.OMEB.domain.user.presentation.api;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "User API", description = "유저 관련 API")
public interface UserApi {
    @Operation(summary = "마이페이지 조회 API", description = "유저의 마이페이지 정보를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = UserInfoResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "NOT_FOUND_USER", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<UserInfoResponse>> getUserInfo(@UserPrincipal CustomUserPrincipal userPrincipal);

    @Operation(summary = "유저 경험치 획득 리스트 조회 API", description = "유저의 경험치 획득 리스트 조회 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = UserExpLogResponse.class),mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBody<List<UserExpLogResponse>>> getUserExpLogs(@UserPrincipal CustomUserPrincipal userPrincipal);

    @Operation(summary = "유저 정보 변경 API", description = "유저의 정보를 변경하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "NOT_FOUND_USER", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0001", description = "닉네임 형식이 올바르지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<Void>> updateUserInfo(
            @UserPrincipal CustomUserPrincipal userPrincipal, @RequestBody @Valid UpdateUserInfoRequest updateUserInfoRequest);

    @Operation(summary = "랭킹 페이지 조회 API", description = "랭킹 페이지 조회 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = UserRankPageResponse.class),mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBody<UserRankPageResponse>> getUserRankList(
            @RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 번호", example = "1") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "페이지 당 유저 수",example = "10") int size);

    @Operation(summary = "사용자 히스토리 저장 API", description = "사용자 히스토리 저장 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<Void>> createHistory(
            @UserPrincipal CustomUserPrincipal userPrincipal,
            @RequestBody HistoryRequest historyRequest);

    @Operation(summary = "사용자 히스토리 조회 API", description = "사용자 히스토리 조회 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = UserHistoryPageResponse.class),mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBody<UserHistoryPageResponse>>  getHistory(
            @UserPrincipal CustomUserPrincipal userPrincipal,
            @RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 넘버(가장 작은 수 1)", example = "1") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "한 페이지의 조회 될 책 수",example = "10") int size,
            @RequestParam(defaultValue = "DESC") @Schema(description = "정렬 방법" , example = "DESC") String sortDirection,
            @RequestParam(defaultValue = "createdAt") @Schema(description = "정렬 기준",example = "createdAt") String sortBy);
}
