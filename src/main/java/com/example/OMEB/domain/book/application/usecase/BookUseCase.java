package com.example.OMEB.domain.book.application.usecase;

import com.example.OMEB.domain.book.application.service.BookCommandService;
import com.example.OMEB.domain.book.application.service.BookQueryService;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.service.NaverBookSearchClient;
import com.example.OMEB.domain.book.presentation.dto.request.BookApplicationRequest;
import com.example.OMEB.domain.book.presentation.dto.request.BookSearchRequest;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookPageResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.book.presentation.dto.response.EmotionBookTitleInfoListResponse;
import com.example.OMEB.domain.book.presentation.dto.response.NaverBookListResponse;
import com.example.OMEB.global.event.persistence.entity.EventView;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.example.OMEB.global.jwt.CustomUserPrincipal;
import com.example.OMEB.global.utils.StringBlankUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookUseCase {
    private final NaverBookSearchClient naverBookSearchClient;
    private final BookQueryService bookQueryService;
    private final BookCommandService bookCommandService;
    private final ApplicationEventPublisher publisher;

    public NaverBookListResponse searchTitleBooks(BookSearchRequest bookSearchRequest) {
        List<NaverBookDTO> naverBookDTOS = naverBookSearchClient.searchBooks(StringBlankUtils.stringAllNotBlank(bookSearchRequest.getTitle()), null);
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
        if(naverBookDTO.getIsbn()==null){
            throw new ServiceException(ErrorCode.INVALID_BOOK);
        }

        bookCommandService.saveBook(naverBookDTO);
    }

    public BookInfoResponse getBook(CustomUserPrincipal userPrincipal,Long bookId) {
        log.info("[BookUseCase] (getBook) get book request: {}", bookId);
        Long userId = null;
        if (userPrincipal != null) userId = userPrincipal.userId();
        publisher.publishEvent(new EventView(userId, bookId, LocalDateTime.now().toString()));
        return bookQueryService.findByBookId(userPrincipal,bookId);
    }

    @Transactional(readOnly = true)
    public BookTitleListResponse getBookReviewRank() {
        log.info("[BookUseCase] (getBookReviewRank) get book review rank request");
        return bookQueryService.findBookListOrderByReviewRank();
    }

    @Transactional(readOnly = true)
    public EmotionBookTitleInfoListResponse getEmotionRank(TagName emotion) {
        log.info("[BookUseCase] (getEmotionRank) get emotion rank request");
        return bookQueryService.findEmotionRank(emotion);
    }

    @Transactional(readOnly = true)
	public BookPageResponse getBookSearch(String title, Pageable pageable) {
        log.info("[BookUseCase] (getBookSearch) get book search request");
        return bookQueryService.findBookSearch(title, pageable);
	}
}
