package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.user.persistence.entity.History;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "HistoryResponse", description = "사용자 히스토리 리스트 조회 응답")
public class HistoryResponse {
    @Schema(description = "히스토리 DB id", example = "1")
    private Long historyId;
    @Schema(description = "사용자 입력 텍스트", example = "매우 화가난다.")
    private String text;
    @Schema(description = "사용자 선택 책 DB id", example = "1233")
    private Long bookId;
    @Schema(description = "사용자 선택 책 제목", example = "어린왕자")
    private String bookTitle;
    @Schema(description = "사용자 선택 책 저자", example = "생텍쥐베리")
    private String author;
    @Schema(description = "사용자 선택 책 이미지", example = "https://shopping-phinf.pstatic.net/main_3249220/32492201224.20220527052655.jpg")
    private String bookImage;
    @Schema(description = "히스토리 생성 시간", example = "2021-08-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static HistoryResponse entityToResponse(History history){
        return HistoryResponse.builder()
                .historyId(history.getId())
                .text(history.getText())
                .bookId(history.getBook().getId())
                .bookTitle(history.getBook().getTitle())
                .author(history.getBook().getAuthor())
                .bookImage(history.getBook().getBookImage())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
