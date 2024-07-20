package com.example.OMEB.domain.review.persistence.repository;

import com.example.OMEB.domain.review.persistence.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
