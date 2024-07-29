package com.example.OMEB.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "UpdateUserInfoRequest", description = "유저 정보 수정 요청")
public class UpdateUserInfoRequest {
    @NotBlank(message = "닉네임은 null 이거나 공백일 수 없습니다.")
    @Schema(description = "닉네임", example = "멋쟁이호랑이")
    private String nickname;
}
