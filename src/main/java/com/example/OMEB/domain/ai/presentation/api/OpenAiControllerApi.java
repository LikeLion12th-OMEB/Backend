package com.example.OMEB.domain.ai.presentation.api;

import com.example.OMEB.domain.ai.presentation.dto.request.UserTextRequest;
import com.example.OMEB.domain.ai.presentation.dto.response.AiTagResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "OpenAi Api", description = "텍스트 -> 태그 변환 API")
public interface OpenAiControllerApi {
    @Operation(summary = "태그 변환 API", description = "태그 변환 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = AiTagResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "TAG_0001", description = "태그 변환을 실패했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "COMMON_0001", description = "텍스트 입력 형식이 올바르지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseBody<AiTagResponse>> convertToTag(@RequestBody @Valid UserTextRequest userTextRequest);
}
