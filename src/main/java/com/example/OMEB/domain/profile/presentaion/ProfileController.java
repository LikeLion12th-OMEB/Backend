package com.example.OMEB.domain.profile.presentaion;

import com.example.OMEB.domain.profile.api.ProfileControllerApi;
import com.example.OMEB.domain.profile.application.service.S3PresignedUrlService;
import com.example.OMEB.domain.profile.application.usecase.ProfileUseCase;
import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@Tag(name = " Profile API", description = "프로필 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController implements ProfileControllerApi {
    private final ProfileUseCase profileUseCase;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/v1/presigned-url")
    public ResponseEntity<ResponseBody<PresignedUrlResponse>> getPresignedUrl(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                                              @RequestParam("fileName") @Schema(description = "파일 이름", example = "example.jpg") String fileName) { // TODO: Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.getPresignedUrl(userPrincipal.userId(), fileName)));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/v1/profile")
    public ResponseEntity<ResponseBody<Void>> createProfile(@UserPrincipal CustomUserPrincipal userPrincipal,
                                                            @RequestParam("url")  @Schema(description = "이미지 접근 URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/prefix/fileId") String url) { //TODO : Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.createProfile(userPrincipal.userId(), url)));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/v1/profile/default")
    public ResponseEntity<ResponseBody<Void>> updateDefaultProfile(@UserPrincipal CustomUserPrincipal userPrincipal) {
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.updateDefaultProfile(userPrincipal.userId())));
    }

}
