package com.example.OMEB.domain.auth.presentation.controller;

import com.example.OMEB.domain.auth.application.service.AuthService;
import com.example.OMEB.domain.auth.presentation.dto.request.TokenRequest;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBody<SignUpResponse>> signUp(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, String> nicknameMap){
        return ResponseEntity.ok(createSuccessResponse(authService.createUser
                (request, response, nicknameMap.get("nickname"))));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ResponseBody<TokenResponse>> reissue(
            @RequestBody TokenRequest tokenRequest){
        return ResponseEntity.ok(createSuccessResponse(authService.reissueToken(tokenRequest)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseBody<Void>> logout(
            @RequestBody Map<String, String> refreshTokenMap){
        authService.logout(refreshTokenMap.get("refreshToken"));
        return ResponseEntity.ok(createSuccessResponse());
    }
}
