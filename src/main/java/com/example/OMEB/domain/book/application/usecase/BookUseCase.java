package com.example.OMEB.domain.book.application.usecase;

import com.example.OMEB.domain.book.application.service.BookCommandService;
import com.example.OMEB.domain.book.application.service.BookQueryService;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.service.NaverBookSearchClient;
import com.example.OMEB.domain.book.presentation.dto.request.BookApplicationRequest;
import com.example.OMEB.domain.book.presentation.dto.request.BookSearchRequest;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.NaverBookListResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookUseCase {
    private final NaverBookSearchClient naverBookSearchClient;
    private final BookQueryService bookQueryService;
    private final BookCommandService bookCommandService;

    //TODO : 검색 title 할 때 띄어쓰기 아예 없어야 검색 결과가 좋아짐!!
    public NaverBookListResponse searchTitleBooks(BookSearchRequest bookSearchRequest) {
        List<NaverBookDTO> naverBookDTOS = naverBookSearchClient.searchBooks(bookSearchRequest.getTitle().replace(" ", ""), null);
        if(naverBookDTOS.size() == 0) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND_BOOK);
        }else if(naverBookDTOS.size() >= 10) {
            throw new ServiceException(ErrorCode.APPLICATION_TOO_MANY_BOOKS);
        }
        log.info("[BookUseCase] (searchTitleBooks) success searched books: {}", naverBookDTOS.size());
        return new NaverBookListResponse(naverBookDTOS);
    }

    @Transactional
    public void applicationBook(BookApplicationRequest bookApplicationRequest) {
        log.info("[BookUseCase] (applicationBook) application book request: {}", bookApplicationRequest);
        bookQueryService.findByISBN(bookApplicationRequest.getIsbn()).ifPresent(book -> {
            throw new ServiceException(ErrorCode.APPLICATION_ALREADY_EXIST_BOOK);
        });
        NaverBookDTO naverBookDTO = naverBookSearchClient.searchBooks(null, bookApplicationRequest.getIsbn()).stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException(ErrorCode.APPLICATION_NOT_FOUND_BOOK));
        bookCommandService.saveBook(naverBookDTO);
    }

    public BookInfoResponse getBook(CustomUserPrincipal userPrincipal,Long bookId) {
        log.info("[BookUseCase] (getBook) get book request: {}", bookId);
        return bookQueryService.findByBookId(userPrincipal,bookId);
    }
}
