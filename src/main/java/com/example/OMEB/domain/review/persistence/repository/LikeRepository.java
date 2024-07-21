package com.example.OMEB.domain.review.persistence.repository;

import com.example.OMEB.domain.review.persistence.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT EXISTS(l) FROM Like l WHERE l.review.id = :reviewId AND l.user.id = :userId")
    boolean existsByReviewIdAndUserId(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
}
