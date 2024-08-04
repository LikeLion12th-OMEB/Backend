package com.example.OMEB.domain.review.persistence.repository;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT new com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse(r.book.id, r.id, r.user.nickname, r.content, r.tag.tagName, COUNT(l.id), r.user.level, r.createdAt, r.updatedAt,r.user.profileImageUrl) " +
            "FROM Review r LEFT JOIN Like l ON l.review.id = r.id " +
            "WHERE r.book.id = :bookId " +
            "GROUP BY r.id, r.book.id, r.user.nickname, r.content, r.tag.tagName, r.user.level, r.createdAt, r.updatedAt")
    Page<ReviewInfoResponse> findAllByBookId(@Param("bookId") Long bookId, Pageable pageable); //TODO : [대회 후TODO]  리팩터링 필요

    @Query("SELECT r FROM Review r " +
            "LEFT JOIN FETCH r.user " +
            "LEFT JOIN FETCH r.book " +
            "LEFT JOIN FETCH r.tag " +
            "LEFT JOIN FETCH r.likes " +
            "WHERE r.user.id = :userId")
    List<Review> findByUser_id(Long userId); //TODO : [대회 후TODO] 리팩터링 필요


    @Query("SELECT new com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse(" +
        "r.book.id, r.id, r.user.nickname, r.content, r.tag.tagName, COUNT(l.id), r.user.level, r.createdAt, r.updatedAt,r.user.profileImageUrl) " +
        "FROM Review r LEFT JOIN Like l ON l.review.id = r.id " +
        "WHERE r.book.id = :bookId " +
        "GROUP BY r.id, r.book.id, r.user.nickname, r.content, r.tag.tagName, r.user.level, r.createdAt, r.updatedAt " +
        "ORDER BY COUNT(l.id) DESC")
    Page<ReviewInfoResponse> findAllByBookIdOrderByLikesDesc(@Param("bookId") Long bookId, Pageable pageable); //TODO : [대회 후TODO] 리팩터링 필요

}
