package com.example.OMEB.domain.auth.presentation.controller;

import com.example.OMEB.domain.auth.application.service.AuthService;
import com.example.OMEB.domain.auth.presentation.api.AuthControllerAPI;
import com.example.OMEB.domain.auth.presentation.dto.request.LogoutRequest;
import com.example.OMEB.domain.auth.presentation.dto.request.SignUpRequest;
import com.example.OMEB.domain.auth.presentation.dto.request.TokenRequest;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerAPI {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseBody<SignUpResponse>> signUp(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Valid SignUpRequest signUpRequest){
        return ResponseEntity.ok(createSuccessResponse(authService.createUser
                (request, response, signUpRequest.getNickname())));
    }

    @PostMapping("/reissue")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseBody<TokenResponse>> reissue(
            @RequestBody @Valid TokenRequest tokenRequest){
        return ResponseEntity.ok(createSuccessResponse(authService.reissueToken(tokenRequest)));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> logout(
            @RequestBody @Valid LogoutRequest logoutRequest){
        authService.logout(logoutRequest.getRefreshToken());
        return ResponseEntity.ok(createSuccessResponse());
    }

    @PostMapping("/check-nickname-duplication")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseBody<Void>> check(
            @RequestParam @Schema(description = "닉네임", example = "멋쟁이사자") String nickname){
        authService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
