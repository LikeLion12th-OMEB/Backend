package com.example.OMEB.domain.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "SignUpRequest", description = "회원가입 요청")
public class SignUpRequest {
    @NotBlank(message = "닉네임은 null 이거나 공백일 수 없습니다.")
    @Schema(description = "닉네임", example = "멋쟁이사자")
    private String nickname;
}
