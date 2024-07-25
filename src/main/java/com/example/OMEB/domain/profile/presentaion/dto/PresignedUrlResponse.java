package com.example.OMEB.domain.profile.presentaion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PresignedUrlResponse", description = "Presigned Url 응답")
public class PresignedUrlResponse {
    @Schema(description = "Presigned Url", example = "https://presignedurl-sample-bucket.s3.amazonaws.com/test.txt?AWSAccessKeyId=ASIATYFK6QMU57LCNAM3&Expires=1549289788&x-amz-security-token=FQoGoiO")
    private String url;

    public PresignedUrlResponse(String url) {
        this.url = url;
    }
}
