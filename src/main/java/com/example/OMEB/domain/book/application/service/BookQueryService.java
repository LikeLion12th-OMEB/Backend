package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookQueryService {

    private final BookRepository bookRepository;

    public Optional<Book> findByISBN(String ISBN) {
        return bookRepository.findByIsbn(ISBN);
    }

    public BookInfoResponse findByBookId(Long bookId) {
        return Book.toBookInfoResponse(bookRepository.findById(bookId).orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOK)));
    }
}
