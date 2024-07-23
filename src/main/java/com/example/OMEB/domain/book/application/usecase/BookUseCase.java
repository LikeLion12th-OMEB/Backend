package com.example.OMEB.domain.book.application.usecase;

import com.example.OMEB.domain.book.application.service.BookCommandService;
import com.example.OMEB.domain.book.application.service.BookQueryService;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.service.NaverBookSearchClient;
import com.example.OMEB.domain.book.presentation.dto.request.BookApplicationRequest;
import com.example.OMEB.domain.book.presentation.dto.request.BookSearchRequest;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookUseCase {
    private final NaverBookSearchClient naverBookSearchClient;
    private final BookQueryService bookQueryService;
    private final BookCommandService bookCommandService;

    public List<NaverBookDTO> searchTitleBooks(BookSearchRequest bookSearchRequest) {
        List<NaverBookDTO> naverBookDTOS = naverBookSearchClient.searchBooks(bookSearchRequest.getTitle(), null);
        if(naverBookDTOS.size() == 0) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND_BOOK);
        }else if(naverBookDTOS.size() >= 10) {
            throw new ServiceException(ErrorCode.APPLICATION_TOO_MANY_BOOKS);
        }
        return naverBookDTOS;
    }

    @Transactional
    public void applicationBook(BookApplicationRequest bookApplicationRequest) {
        bookQueryService.findByISBN(bookApplicationRequest.getIsbn()).ifPresent(book -> {
            throw new ServiceException(ErrorCode.APPLICATION_ALREADY_EXIST_BOOK);
        });
        NaverBookDTO naverBookDTO = naverBookSearchClient.searchBooks(null, bookApplicationRequest.getIsbn()).stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException(ErrorCode.APPLICATION_NOT_FOUND_BOOK));
        bookCommandService.saveBook(naverBookDTO);
    }

    public BookInfoResponse getBook(Long bookId) {
        return bookQueryService.findByBookId(bookId);
    }
}
