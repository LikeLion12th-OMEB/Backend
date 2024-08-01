package com.example.OMEB.domain.book.api;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookPageResponse;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleListResponse;
import com.example.OMEB.domain.book.presentation.dto.response.EmotionBookTitleInfoListResponse;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;
import com.example.OMEB.global.utils.StringBlankUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book API", description = "책 정보 관련 API")
public interface BookInfoControllerApi {
	@Operation(summary = "책 상세 조회 API", description = "책의 상세 정보를 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {
				@Content(schema = @Schema(implementation = BookInfoResponse.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@UserPrincipal CustomUserPrincipal userPrincipal,
		@PathVariable @Schema(description = "책 id", example = "1") Long bookId);

	@Operation(summary = "책 리뷰 순 조회 API", description = "책 리뷰 순으로 10권 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
	})
	ResponseEntity<ResponseBody<BookTitleListResponse>> getBookReviewRank();

	@Operation(summary = "감정 기반 책 추천 API", description = "감정 별 인기 많은 책을 30권 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {
				@Content(schema = @Schema(implementation = EmotionBookTitleInfoListResponse.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "COMMON_0008", description = "감정 태그 입력이 잘못되었습니다.", content = @Content(mediaType = "application/json"))
	})
	ResponseEntity<ResponseBody<EmotionBookTitleInfoListResponse>> getEmotionRank(@RequestParam("emotion") @Schema(description = "감정", example = "ANGER") TagName emotion);

	@Operation(summary = "책 검색 API", description = "책 제목을 검색하여 조회하는 API.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = {
				@Content(schema = @Schema(implementation = BookPageResponse.class), mediaType = "application/json")})
	})
	ResponseEntity<ResponseBody<BookPageResponse>> getBookSearch(@RequestParam("title") @Schema(description = "책 제목", example = "자바의 정석") String title,
		@RequestParam(defaultValue = "1") @Schema(description = "조회할 페이지 넘버(가장 작은 수 1)", example = "1") int page,
		@RequestParam(defaultValue = "10") @Schema(description = "한 페이지의 조회 될 책 수",example = "10") int size,
		@RequestParam(defaultValue = "DESC") @Schema(description = "정렬 방법" , example = "DESC") String sortDirection,
		@RequestParam(defaultValue = "createdAt") @Schema(description = "정렬 기준",example = "createdAt") String sortBy);
}