package com.example.OMEB.domain.auth.presentation.api;

import com.example.OMEB.domain.auth.presentation.dto.response.SignUpResponse;
import com.example.OMEB.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Temporary Auth Api", description = "테스트용 임시 API")
public interface TemporaryAuthApi {

    @Operation(summary = "임시 Jwt 토큰 요청 API", description = "임시 Jwt 토큰 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = TokenResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "데이터베이스에 id가 1인 유저가 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<TokenResponse>> getToken();
}
