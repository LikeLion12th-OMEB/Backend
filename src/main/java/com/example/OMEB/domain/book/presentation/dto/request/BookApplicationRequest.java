package com.example.OMEB.domain.book.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "BookApplicationRequest", description = "책 신청 요청")
public class BookApplicationRequest {

    @NotBlank(message = "ISBN을 입력해주세요.")
    @Schema(description = "ISBN (책 고유 번호)", example = "9788968480011")
    private String isbn;
}
