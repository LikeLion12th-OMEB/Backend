package com.example.OMEB.domain.auth.presentation.api;

import com.example.OMEB.domain.auth.presentation.dto.request.LogoutRequest;
import com.example.OMEB.domain.auth.presentation.dto.request.SignUpRequest;
import com.example.OMEB.domain.auth.presentation.dto.request.TokenRequest;
import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth API", description = "인증/인가 관련 API")
public interface AuthControllerAPI {
    @Operation(summary = "회원가입 API", description = "회원가입 요청 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = SignUpResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "AUTH_0001", description = "회원가입 제한시간이 초과했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0001", description = "닉네임 입력 형식이 올바르지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<SignUpResponse>> signUp(
            HttpServletRequest request, HttpServletResponse response,
            @RequestBody @Valid SignUpRequest signUpRequest);

    @Operation(summary = "Jwt 토큰 재발급 API", description = "Jwt 토큰 재발급 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = TokenResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "AUTH_0002", description = "리프레시 토큰이 만료되었습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH_0003", description = "잘못된 리프레시 토큰입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH_0005", description = "토큰 관련 잘못된 접근입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0001", description = "JWT 토큰 입력 형식이 올바르지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<TokenResponse>> reissue(
            @RequestBody @Valid TokenRequest tokenRequest);

    @Operation(summary = "로그아웃 API", description = "로그아웃 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "COMMON_0001", description = "JWT 토큰 입력 형식이 올바르지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<Void>> logout(
            @RequestBody @Valid LogoutRequest logoutRequest);

    @Operation(summary = "중복 닉네임 확인 API", description = "중복 닉네임 확인 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0002", description = "중복된 닉네임입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<Void>> check(
            @RequestParam @Schema(description = "닉네임", example = "멋쟁이사자") String nickname);
}
