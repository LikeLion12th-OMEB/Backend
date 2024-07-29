package com.example.OMEB.domain.user.persistence.repository;

import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.presentation.dto.response.rank.UserRankInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT new com.example.OMEB.domain.user.presentation.dto.response.rank.UserRankInfoResponse(u.id, u.nickname, u.level, u.exp, u.profileImageUrl) " +
            "FROM User u " +
            "ORDER BY u.level DESC , u.exp desc")
    Page<UserRankInfoResponse> findAllUsersByPage(Pageable pageable);
    User findByProviderId(String ProviderId);
    Optional<User> findByNickname(String nickname);
}
