package com.example.OMEB.domain.book.presentation;

import com.example.OMEB.domain.book.application.service.BookMarkServiceImpl;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.base.dto.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookMarkController {
    private final BookMarkServiceImpl bookMarkService;

    @PostMapping("/v1/bookmark/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> saveBookMark(Long userId , @PathVariable Long bookId) {
        bookMarkService.saveBookMark(userId, bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    @GetMapping("/v1/bookmark")
    @AssignUserId
    public ResponseEntity<ResponseBody<List<BookTitleInfoResponse>>> getBookMark(Long userId) {
        return ResponseEntity.ok(createSuccessResponse(bookMarkService.findUserBookMark(userId)));
    }

    @DeleteMapping("/v1/bookmark/{bookId}")
    @AssignUserId
    public ResponseEntity<ResponseBody<Void>> deleteBookMark(Long userId, @PathVariable Long bookId) {
        bookMarkService.deleteBookMark(userId, bookId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
