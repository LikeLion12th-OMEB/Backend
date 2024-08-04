package com.example.OMEB.domain.ai.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "UserTextRequest", description = "태그 변환 요청")
public class UserTextRequest {
    @NotEmpty(message = "텍스트가 비어서는 안됩니다.")
    @Schema(description = "유저 입력 텍스트", example = "많이 짜증나고 화난다.")
    private String text;
}
