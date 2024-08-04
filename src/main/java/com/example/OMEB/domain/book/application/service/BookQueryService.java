package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.EmotionRank;
import com.example.OMEB.domain.bookmark.persistence.repository.BookMarkRepository;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.persistence.repository.EmotionRankRepository;
import com.example.OMEB.domain.book.presentation.dto.BookTitleInfo;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookPageResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.book.presentation.dto.response.EmotionBookTitleInfoListResponse;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookQueryService {

    private final BookRepository bookRepository;
    private final BookMarkRepository bookMarkRepository;
    private final EmotionRankRepository emotionRankRepository;

    public Optional<Book> findByISBN(String ISBN) {
        return bookRepository.findByIsbn(ISBN);
    }

    public BookInfoResponse findByBookId(CustomUserPrincipal userPrincipal, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOK));
        if (userPrincipal == null) {
            return Book.toBookInfoResponse(book, false);
        }
        log.info("[BookQueryService] (findByBookId) get book request: {}", bookId);
        return Book.toBookInfoResponse(book, bookMarkRepository.existsUserIdAndBookId(userPrincipal.userId(),  bookId));
    }
    public BookTitleListResponse findBookListOrderByReviewRank() {
        log.info("[BookQueryService] (findBookListOrderByReviewRank) get book review rank request");
		List<BookTitleInfoResponse> list = bookRepository.findTopBooksByReviewRank()
            .stream()
            .map(BookTitleInfoResponse::new)
            .toList();

        log.info("[BookQueryService] (findBookListOrderByReviewRank) success get book review rank: {}", list.size());
        return new BookTitleListResponse(list);
    }

    public EmotionBookTitleInfoListResponse findEmotionRank(TagName emotion) {
        log.info("[BookQueryService] (findEmotionRank) get emotion rank request");
        EmotionRank byEmotion = emotionRankRepository.findByEmotion(emotion.toString());
        List<BookTitleInfo> bookTitleInfos = byEmotion.getBookTitleInfos();

        return new EmotionBookTitleInfoListResponse(byEmotion.getEmotion(), bookTitleInfos);
    }

    public BookPageResponse findBookSearch(String title, Pageable pageable) {
        return new BookPageResponse(bookRepository.findByTitleContaining(title, pageable));
    }
}
