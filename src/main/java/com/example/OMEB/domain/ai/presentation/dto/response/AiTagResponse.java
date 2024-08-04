package com.example.OMEB.domain.ai.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AiTagResponse", description = "태그 변환 결과 응답")
public class AiTagResponse {
    @Schema(description = "태그", example = "ANXIETY")
    private String tag;
}
