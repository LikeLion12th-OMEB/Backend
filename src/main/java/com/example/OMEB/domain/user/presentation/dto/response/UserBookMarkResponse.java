package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.BookMark;
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
@Schema(name = "UserBookMarksResponse", description = "유저 북마크 리스트 응답")
public class UserBookMarkResponse {
    @Schema(description = "북마크 DB id", example = "1")
    private Long id;
    @Schema(description = "책 DB id", example = "5")
    private Long bookId;
    @Schema(description = "책 이름", example = "이기적 유전자")
    private String bookTitle;
    @Schema(description = "책 저자", example = "이기적 유전자")
    private String author;
    @Schema(description = "북마크 생성 시간", example = "2021-08-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static UserBookMarkResponse entityToResponse(BookMark bookMark){
        return UserBookMarkResponse.builder()
                .id(bookMark.getId())
                .bookId(bookMark.getBook().getId())
                .bookTitle(bookMark.getBook().getTitle())
                .author(bookMark.getBook().getAuthor())
                .createdAt(bookMark.getCreatedAt())
                .build();
    }
}
