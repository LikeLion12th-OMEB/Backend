package com.example.OMEB.domain.book.presentation;

import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.usecase.BookUseCase;
import com.example.OMEB.domain.book.presentation.dto.request.BookApplicationRequest;
import com.example.OMEB.domain.book.presentation.dto.request.BookSearchRequest;
import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookApplicationController {

    private final BookUseCase bookUseCase;

    @GetMapping("/v1/books/application")
    public ResponseEntity<ResponseBody<List<NaverBookDTO>>> serachBooks(@ModelAttribute @Valid BookSearchRequest bookSearchRequest) {
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.searchTitleBooks(bookSearchRequest)));
    }

    @PostMapping("/v1/book")
    public ResponseEntity<ResponseBody<Void>> applicationBook(@RequestBody @Valid BookApplicationRequest bookApplicationRequest) {
        bookUseCase.applicationBook(bookApplicationRequest);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/v1/book/{bookId}")
    public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(createSuccessResponse(bookUseCase.getBook(bookId)));
    }

}
