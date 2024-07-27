package com.example.OMEB.domain.profile.api;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = " Profile API", description = "프로필 관련 API")
public interface ProfileControllerApi {

	@Operation(summary = "Presigned Url 요청", description = "S3에 파일을 업로드하기 위한 Presigned Url을 요청하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = PresignedUrlResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<PresignedUrlResponse>> getPresignedUrl(@Schema(hidden = true) Long userId,
		@RequestParam("fileName") @Schema(description = "파일 이름", example = "example.jpg") String fileName);


	@Operation(summary = "Profile 이미지 url 저장", description = "Profile 이미지 url을 저장하는 API[해당 이미지로 프로필 이미지가 됨 ].")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> createProfile(@Schema(hidden = true) Long userId,
		@RequestParam("url")  @Schema(description = "이미지 접근 URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/prefix/fileId") String url);


	@Operation(summary = "기본 이미지로 url 변경", description = "기본 이미지로 url을 변경하는 API[해당 이미지로 프로필 이미지가 됨 ].")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> updateDefaultProfile(@Schema(hidden = true) Long userId);
}
