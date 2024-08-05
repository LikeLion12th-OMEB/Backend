package com.example.OMEB.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "HistoryRequest", description = "사용자 히스토리 저장 요청")
public class HistoryRequest {
    @NotEmpty(message = "text는 null 이거나 공백일 수 없습니다.")
    @Schema(description = "사용자 입력 텍스트", example = "매우 화가난다.")
    private String text;
    @NotNull(message = "book 아이디는 null 일 수 없습니다.")
    @Schema(description = "사용자 선택 책 DB id", example = "1233")
    private Long bookId;
}
