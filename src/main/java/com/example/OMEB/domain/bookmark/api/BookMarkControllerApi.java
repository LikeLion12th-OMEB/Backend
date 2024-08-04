package com.example.OMEB.domain.bookmark.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.bookmark.presentation.dto.response.BookMarkBookTitleListResponse;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = " BookMark API", description = "북마크 관련 API")
public interface BookMarkControllerApi {

	@Operation(summary = "BookMark 저장 API", description = "북마크를 저장하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "BOOK_0005", description = "해당 책은 이미 북마크 되어있습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> saveBookMark(@UserPrincipal CustomUserPrincipal userPrincipal , @PathVariable @Schema(description = "책 id",example = "1") Long bookId);

	@Operation(summary = "특정 회원의 북마크 리스트 조회 API", description = "특정 회원의 북마크 리스트를 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = BookMarkBookTitleListResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "USER_0001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<BookMarkBookTitleListResponse>> getBookMark(@UserPrincipal CustomUserPrincipal userPrincipal);

	@Operation(summary = "북마크 해제 API", description = "북마크를 해제하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {@Content(schema = @Schema(implementation = Void.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "Book_0006", description = "해당 책은 북마크 되어있지 않습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<Void>> deleteBookMark(@UserPrincipal CustomUserPrincipal userPrincipal,
		@PathVariable @Schema(description = "책 id",example = "1") Long bookId);
}
