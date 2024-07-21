package com.example.OMEB.domain.review.persistence.repository;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.Tag;
import com.example.OMEB.domain.review.persistence.entity.Like;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.domain.review.presentation.dto.response.ReviewInfoResponse;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.oauth.user.OAuth2Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Long bookId;
    private Long reviewId1;

    private Long reviewId2;

    @BeforeEach
    void setUp() {
        // 유저 생성
        User user = User.builder()
                .nickname("testuser")
                .provider(OAuth2Provider.GOOGLE) // 예시로 Google을 사용
                .level(1)
                .build();
        entityManager.persist(user);

        // 태그 생성
        Tag tag = new Tag();
        tag.setTagName(TagName.ANGER); // 예시로 EXAMPLE을 사용
        entityManager.persist(tag);

        // 책 생성
        Book book = Book.builder()
                .title("Test Book")
                .description("Description")
                .author("Author")
                .publisher("Publisher")
                .publicationDate("2023-01-01")
                .bookImage("image_url")
                .price("10000")
                .sellLink("sell_link")
                .tag(tag)
                .build();
        entityManager.persist(book);
        bookId = book.getId();

        // 첫 번째 리뷰 생성
        Review review1 = Review.builder()
                .content("This is a test review 1.")
                .user(user)
                .book(book)
                .tag(tag)
                .build();
        entityManager.persist(review1);
        reviewId1 = review1.getId();

        // 두 번째 리뷰 생성
        Review review2 = Review.builder()
                .content("This is a test review 2.")
                .user(user)
                .book(book)
                .tag(tag)
                .build();
        entityManager.persist(review2);
        reviewId2 = review2.getId();

        // 첫 번째 리뷰에 좋아요 추가
        Like like = new Like(user, review1);
        entityManager.persist(like);

        entityManager.flush();
    }

    @Test
    void testFindAllByBookId() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC,"createdAt"); // 첫 페이지에 10개의 리뷰를 가져옴
//        Page<ReviewInfoResponse> reviewsPage = reviewRepository.findAllByBookId(bookId, pageable);
//
//        assertNotNull(reviewsPage);
//        List<ReviewInfoResponse> reviews = reviewsPage.getContent();
//        assertEquals(2, reviews.size());
//        ReviewInfoResponse review = reviews.get(0);
//        assertEquals("This is a test review 1.", review.getContent());
//        assertEquals("testuser", review.getUserNickname());
//        assertEquals("ANGER", review.getTag());
//        assertEquals(1, review.getLikeCount());
//        assertEquals(1, review.getLevel());
//        assertEquals(bookId, review.getBookId());
//        assertEquals(reviewId1, review.getReviewId());
//        assertEquals("testuser", review.getUserNickname());
//        System.out.println("review.getCreatedAt() = " + review.getCreatedAt());
//        System.out.println("review.getUpdatedAt() = " + review.getUpdatedAt());


    }

}