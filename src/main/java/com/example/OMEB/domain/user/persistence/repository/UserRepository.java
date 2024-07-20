package com.example.OMEB.domain.user.persistence.repository;

import com.example.OMEB.domain.user.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
