package com.example.OMEB.domain.profile.presentaion;

import com.example.OMEB.domain.profile.application.service.S3PresignedUrlService;
import com.example.OMEB.domain.profile.application.usecase.ProfileUseCase;
import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@Tag(name = " Profile API", description = "프로필 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {
    private final ProfileUseCase profileUseCase;

    @Operation(summary = "Presigned Url 요청", description = "S3에 파일을 업로드하기 위한 Presigned Url을 요청하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = PresignedUrlResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/v1/presigned-url")
    @AssignUserId
    public ResponseEntity<ResponseBody<PresignedUrlResponse>> getPresignedUrl(Long userId,
                                                                              @RequestParam("fileName") @Schema(description = "파일 이름", example = "example.jpg") String fileName) { // TODO: Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.getPresignedUrl(userId, fileName)));
    }

    @Operation(summary = "Profile 이미지 url 저장", description = "Profile 이미지 url을 저장하는 API[해당 이미지로 프로필 이미지가 됨 ].")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/v1/profile")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> createProfile(Long userId,
                                                            @RequestParam("url")  @Schema(description = "이미지 접근 URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/prefix/fileId") String url) { //TODO : Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.createProfile(userId, url)));
    }

    @Operation(summary = "기본 이미지로 url 변경", description = "기본 이미지로 url을 변경하는 API[해당 이미지로 프로필 이미지가 됨 ].")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/v1/profile/default")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> updateDefaultProfile(Long userId) {
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.updateDefaultProfile(userId)));
    }

}
