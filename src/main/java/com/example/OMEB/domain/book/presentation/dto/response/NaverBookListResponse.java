package com.example.OMEB.domain.book.presentation.dto.response;

import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "NaverBookListResponse", description = "네이버 책 리스트 응답")
public class NaverBookListResponse {
    @Schema(description = "네이버 책 정보 리스트",implementation = NaverBookDTO.class)
    private List<NaverBookDTO> naverBookDTOList;

    public NaverBookListResponse(List<NaverBookDTO> naverBookDTOList) {
        this.naverBookDTOList = naverBookDTOList;
    }
}
