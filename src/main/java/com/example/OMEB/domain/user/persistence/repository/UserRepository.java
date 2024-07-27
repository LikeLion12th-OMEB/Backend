package com.example.OMEB.domain.user.persistence.repository;

import com.example.OMEB.domain.user.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByProviderId(String ProviderId);
}
