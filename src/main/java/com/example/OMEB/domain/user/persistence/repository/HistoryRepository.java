package com.example.OMEB.domain.user.persistence.repository;

import com.example.OMEB.domain.user.persistence.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT h FROM History h " +
            "LEFT JOIN FETCH h.user " +
            "LEFT JOIN FETCH h.book " +
            "WHERE h.user.id = :userId")
    Page<History> findPageByIdFetch(Long userId, Pageable pageable);
}
