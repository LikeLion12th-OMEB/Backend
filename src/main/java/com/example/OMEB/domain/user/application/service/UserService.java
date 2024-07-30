package com.example.OMEB.domain.user.application.service;

import com.example.OMEB.domain.user.application.IncreaseExpType;
import com.example.OMEB.domain.user.persistence.entity.ExpLog;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.ExpLogRepository;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.domain.user.presentation.dto.response.UserExpLogResponse;
import com.example.OMEB.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.OMEB.domain.user.presentation.dto.response.rank.UserRankInfoResponse;
import com.example.OMEB.domain.user.presentation.dto.response.rank.UserRankPageResponse;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ExpLogRepository expLogRepository;

    public UserInfoResponse getUserInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        return UserInfoResponse.entityToResponse(user);
    }



    public List<UserExpLogResponse> getUserExpLogs(Long userId){
        List<ExpLog> expLogList = expLogRepository.findByUser_id(userId);

        return expLogList.stream()
                .map(UserExpLogResponse::entityToResponse).toList();
    }

    @Transactional
    public void updateUserInfo(Long userId, String nickname){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        user.updateNickname(nickname);
    }

    public UserRankPageResponse getUserRankList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserRankInfoResponse> userRankListResponsePage = userRepository.findAllUsersByPage(pageable);
        return UserRankPageResponse.pageToResponse(userRankListResponsePage);
    }

    public void increaseExp(User user, IncreaseExpType type){
        int increasedExp = user.getExp() + type.getExp();
        int level = user.getLevel();
        int levelExpSize = 100 * (int)Math.pow(2, level-1);
        if ((levelExpSize - increasedExp) <= 0){
            ++level;
            increasedExp -= levelExpSize;
            user.updateLevel(level);
        }
        user.updateExp(increasedExp);

        expLogRepository.save(ExpLog.builder()
                .exp(type.getExp())
                .content(type.getContent())
                .user(user)
                .build());
    }
}
