package com.example.OMEB.domain.book.persistence.repository;

import com.example.OMEB.domain.book.persistence.entity.BookMark;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("select " +
            "new com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse" +
            "(b.book.id, b.book.title, b.book.author, b.book.bookImage, b.book.price) " +
            "from BookMark b where b.user.id = :id")
    List<BookTitleInfoResponse> findBookTitleInfoResponseByUserId(Long id);

    @Query("SELECT EXITSTS(b) FROM BookMark b WHERE b.user.id = :userId AND b.book.id = :bookId")
    Boolean existsUserIdAndBookId(Long userId, Long bookId);
}
