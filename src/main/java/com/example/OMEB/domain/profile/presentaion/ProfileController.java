package com.example.OMEB.domain.profile.presentaion;

import com.example.OMEB.domain.profile.application.service.S3PresignedUrlService;
import com.example.OMEB.domain.profile.application.usecase.ProfileUseCase;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {
    private final ProfileUseCase profileUseCase;


    @GetMapping("/v1/presigned-url")
    @AssignUserId
    public ResponseEntity<ResponseBody<Map<String,String>>> getPresignedUrl(Long userId, @RequestParam("fileName") String fileName) { // TODO: Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.getPresignedUrl(userId, fileName)));
    }

    @PatchMapping("/v1/profile")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> createProfile(Long userId, @RequestParam("url") String url) { //TODO : Validation 검사를 위해 한번 생각해봐야 함
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.createProfile(userId, url)));
    }

    @PatchMapping("/v1/profile/default")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> updateDefaultProfile(Long userId) {
        return ResponseEntity.ok(createSuccessResponse(profileUseCase.updateDefaultProfile(userId)));
    }

}
