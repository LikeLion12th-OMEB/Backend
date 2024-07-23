package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.entity.BookMark;
import com.example.OMEB.domain.book.persistence.repository.BookMarkRepository;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.domain.user.persistence.repository.UserRepository;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    public void saveBookMark(Long userId, Long bookId) {
        bookMarkRepository.findByUserIdAndBookId(userId, bookId).ifPresent(bookMark -> {
            throw new ServiceException(ErrorCode.APPLICATION_ALREADY_EXIST_BOOKMARK);
        });
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOK));

        BookMark bookMark = BookMark.createBookMark(user, book);
        bookMarkRepository.save(bookMark);
    }

    public List<BookTitleInfoResponse> findUserBookMark(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));
        return bookMarkRepository.findBookTitleInfoResponseByUserId(user.getId());
    }

    public void deleteBookMark(Long userId, Long bookId) {
        BookMark bookMark = bookMarkRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_BOOKMARK));
        bookMarkRepository.delete(bookMark);
    }
}
