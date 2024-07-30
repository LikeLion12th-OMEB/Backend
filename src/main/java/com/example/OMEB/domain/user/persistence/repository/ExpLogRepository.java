package com.example.OMEB.domain.user.persistence.repository;

import com.example.OMEB.domain.user.persistence.entity.ExpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpLogRepository extends JpaRepository<ExpLog, Long> {
    @Query("SELECT e FROM ExpLog e " +
            "LEFT JOIN FETCH e.user " +
            "WHERE e.user.id =:userId")
    List<ExpLog> findByUser_id(Long userId);
}
