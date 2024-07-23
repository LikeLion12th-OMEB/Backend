package com.example.OMEB.domain.book.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookApplicationRequest {

    @NotBlank(message = "ISBN을 입력해주세요.")
    private String isbn;
}
