package com.example.OMEB.domain.profile.application.service;

import com.example.OMEB.domain.profile.application.ProfileUrlUtill;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final ProfileUrlUtill profileUrlUtill;

    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public Void saveProfile(Long userId, String url) {
        User user = validateUser(userId);
        user.updateProfileImageUrl(profileUrlUtill.validateUrl(url,user.getNickname()));
        return null;
    }

    @Transactional
    public Void updateDefaultProfile(Long userId) {
        User user = validateUser(userId);
        user.updateProfileImageUrl(profileUrlUtill.getDefaultUrl());
        return null;
    }
}
