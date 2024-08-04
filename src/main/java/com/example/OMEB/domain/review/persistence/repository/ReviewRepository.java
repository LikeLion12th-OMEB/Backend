package com.example.OMEB.domain.review.persistence.repository;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.review.presentation.dto.response.UserReviewResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT new com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse(r.book.id, r.id, r.user.nickname, r.content, r.tag.tagName, COUNT(l.id), r.user.level, r.createdAt, r.updatedAt,r.user.profileImageUrl) " +
            "FROM Review r LEFT JOIN Like l ON l.review.id = r.id " +
            "WHERE r.book.id = :bookId " +
            "GROUP BY r.id, r.book.id, r.user.nickname, r.content, r.tag.tagName, r.user.level, r.createdAt, r.updatedAt")
    Page<ReviewInfoResponse> findAllByBookId(@Param("bookId") Long bookId, Pageable pageable); //TODO : [대회 후TODO]  리팩터링 필요


    @Query("SELECT new com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse(" +
        "r.book.id, r.id, r.user.nickname, r.content, r.tag.tagName, COUNT(l.id), r.user.level, r.createdAt, r.updatedAt,r.user.profileImageUrl) " +
        "FROM Review r LEFT JOIN Like l ON l.review.id = r.id " +
        "WHERE r.book.id = :bookId " +
        "GROUP BY r.id, r.book.id, r.user.nickname, r.content, r.tag.tagName, r.user.level, r.createdAt, r.updatedAt " +
        "ORDER BY COUNT(l.id) DESC")
    Page<ReviewInfoResponse> findAllByBookIdOrderByLikesDesc(@Param("bookId") Long bookId, Pageable pageable); //TODO : [대회 후TODO] 리팩터링 필요

    @Query("SELECT new com.example.OMEB.domain.review.presentation.dto.response.UserReviewResponse(" +
            "r.id, r.content,r.tag.tagName,COUNT(l.id) ,r.book.id, r.book.title,r.book.bookImage,  r.createdAt, r.updatedAt) " +
            "FROM Review r LEFT JOIN Like l ON l.review.id = r.id " +
            "WHERE r.user.id = :userId " +
            "GROUP BY r.id, r.content,r.tag.tagName, r.book.id, r.book.title,r.book.bookImage, r.createdAt, r.updatedAt")//TODO : [대회 후TODO] 리팩터링 필요
    Page<UserReviewResponse> findReviewPageByUserId(Long userId, Pageable pageable);
}
