package com.example.OMEB.domain.book.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "BookSearchRequest", description = "책 검색 요청")
public class BookSearchRequest {

    @NotBlank(message = "검색어를 입력해주세요.")
    @Size(min = 1, max = 100, message = "검색어는 1자 이상 100자 이하로 입력해주세요.")
    @Schema(description = "검색어", example = "자바의 정석")
    private String title;
}
