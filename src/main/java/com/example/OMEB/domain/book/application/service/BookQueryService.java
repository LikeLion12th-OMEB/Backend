package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.repository.BookMarkRepository;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookQueryService {

    private final BookRepository bookRepository;
    private final BookMarkRepository bookMarkRepository;

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
}
