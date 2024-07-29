package com.example.OMEB.domain.review.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.review.persistence.entity.Tag;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.persistence.repository.TagRepository;
import com.example.OMEB.domain.review.persistence.entity.Like;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.persistence.repository.LikeRepository;
import com.example.OMEB.domain.review.persistence.repository.ReviewRepository;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewCreateRequest;
import com.example.OMEB.domain.review.presentation.dto.request.ReviewUpdateRequest;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewPageResponse;
import com.example.OMEB.domain.user.application.IncreaseExpType;
import com.example.OMEB.domain.user.application.service.UserService;
import com.example.OMEB.domain.user.persistence.entity.ExpLog;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.ExpLogRepository;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final ExpLogRepository expLogRepository;

    private final UserService userService;

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

        ExpLog explog = userService.increaseExp(user, IncreaseExpType.WRITE_REVIEW);
        expLogRepository.save(explog);
        return ReviewInfoResponse.builder()
                .bookId(bookId)
                .reviewId(review.getId())
                .userNickname(user.getNickname())
                .content(review.getContent())
                .tag(review.getTag().getTagName())
                .likeCount(0L)
                .level(user.getLevel())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
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
                .tag(review.getTag().getTagName())
                .likeCount(0L)
                .level(user.getLevel())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }


    @Transactional(readOnly = true)
    public ReviewPageResponse getReviews(Long bookId, int page, int size, String sortDirection, String sortBy) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOK));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy);
        return new ReviewPageResponse(reviewRepository.findAllByBookId(bookId, pageable));

    }

    @Transactional
    public void likeReview(Long userId,Long reviewId) {
        if(likeRepository.existsByReviewIdAndUserId(reviewId, userId)){
           throw new ServiceException(ErrorCode.ALREADY_LIKE_REVIEW);
        }
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_REVIEW));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        Like like = new Like(user,review);
        likeRepository.save(like);

        ExpLog createLikeExplog = userService.increaseExp(user, IncreaseExpType.CREATE_LIKE);
        expLogRepository.save(createLikeExplog);

        User reviewUser = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        ExpLog getLikeExplog = userService.increaseExp(reviewUser, IncreaseExpType.GET_LIKE);
        expLogRepository.save(getLikeExplog);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_REVIEW));
        if(!review.getUser().getId().equals(userId)) {
            throw new ServiceException(ErrorCode.REVIEW_NOT_MATCH_USER);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        review.getUser().getReviews().remove(review);
        reviewRepository.delete(review);
    }
}
