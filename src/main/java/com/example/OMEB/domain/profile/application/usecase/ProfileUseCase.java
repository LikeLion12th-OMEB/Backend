package com.example.OMEB.domain.profile.application.usecase;

import com.example.OMEB.domain.profile.application.service.ProfileService;
import com.example.OMEB.domain.profile.application.service.S3PresignedUrlService;
import com.example.OMEB.domain.profile.presentaion.dto.PresignedUrlResponse;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileUseCase {
    private final S3PresignedUrlService s3PresignedUrlService;
    private final ProfileService profileService;
    public PresignedUrlResponse getPresignedUrl(Long userId, String fileName) {
        User user = profileService.validateUser(userId);
        return s3PresignedUrlService.getPresignedUrl(user.getNickname(), fileName);
    }

    @Transactional
    public Void createProfile(Long userId, String url) {
        return profileService.saveProfile(userId, url);
    }

    @Transactional
    public Void updateDefaultProfile(Long userId) {
        return profileService.updateDefaultProfile(userId);
    }

}
