package com.example.OMEB.domain.review.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.Tag;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.persistence.repository.TagRepository;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.persistence.repository.ReviewRepository;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewUpdateRequest;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;


    @Transactional
    public ReviewInfoResponse createReview(Long userId, Long bookId, ReviewCreateRequest reviewCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOK));
        Tag tag = tagRepository.findByTagName(reviewCreateRequest.getTag().toString())
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_TAG));
        Review review = Review.fromReviewRequest(user, book, reviewCreateRequest,tag);
        reviewRepository.save(review);
        return ReviewInfoResponse.builder()
                .bookId(bookId)
                .reviewId(review.getId())
                .userNickname(user.getNickname())
                .content(review.getContent())
                .tag(review.getTag().toString())
                .likeCount(0L)
                .level(user.getLevel())
                .createdAt(review.getCreatedAt().toString())
                .updatedAt(review.getUpdatedAt().toString())
                .build();
    }

    @Transactional
    public ReviewInfoResponse updateReview(Long userId, Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_REVIEW));
        if(review.getUser().getId().equals(userId)) {
            throw new ServiceException(ErrorCode.REVIEW_NOT_MATCH_USER);
        }
        Tag preTag = review.getTag();

        if(!preTag.toString().equals(reviewUpdateRequest.getTag())) {
            Tag newTag = tagRepository.findByTagName(reviewUpdateRequest.getTag().toString())
                    .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_TAG));
            preTag.getReviews().remove(review);
            preTag = newTag;
        }
        review.updateReview(reviewUpdateRequest.getContent(),preTag);
        return ReviewInfoResponse.builder()
                .bookId(review.getBook().getId())
                .reviewId(review.getId())
                .userNickname(user.getNickname())
                .content(review.getContent())
                .tag(review.getTag().toString())
                .likeCount(0L)
                .level(user.getLevel())
                .createdAt(review.getCreatedAt().toString())
                .updatedAt(review.getUpdatedAt().toString())
                .build();
    }
}
