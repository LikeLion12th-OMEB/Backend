package com.example.OMEB.domain.book.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class BookSearchRequest {

    @NotBlank(message = "검색어를 입력해주세요.")
    @Size(min = 1, max = 100, message = "검색어는 1자 이상 100자 이하로 입력해주세요.")
    private String title;
}
