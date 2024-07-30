package com.example.OMEB.domain.user.presentation.dto.response;

import com.example.OMEB.domain.user.persistence.entity.ExpLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserExpLogsResponse", description = "유저 경험치 획득 리스트 응답")
public class UserExpLogResponse {
    @Schema(description = "경험치 로그 DB id", example = "1")
    private Long id;
    @Schema(description = "현재 경험치 양", example = "20")
    private Integer exp;
    @Schema(description = "경험치 획득 내용", example = "리뷰 작성")
    private String content;
    @Schema(description = "경험치 획득 시간", example = "2021-08-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static UserExpLogResponse entityToResponse(ExpLog expLog){
        return UserExpLogResponse.builder()
                .id(expLog.getId())
                .exp(expLog.getExp())
                .content(expLog.getContent())
                .createdAt(expLog.getCreatedAt())
                .build();
    }
}
