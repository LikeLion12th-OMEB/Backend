package com.example.OMEB.domain.book.api;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.OMEB.domain.book.presentation.dto.response.BookInfoResponse;
import com.example.OMEB.global.aop.AssignUserId;
import com.example.OMEB.global.aop.UserPrincipal;
import com.example.OMEB.global.base.dto.ResponseBody;
import com.example.OMEB.global.jwt.CustomUserPrincipal;

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
			content = {@Content(schema = @Schema(implementation = BookInfoResponse.class),mediaType = "application/json")}),
		@ApiResponse(responseCode = "BOOK_0001", description = "책을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
	})
	public ResponseEntity<ResponseBody<BookInfoResponse>> getBook(@UserPrincipal CustomUserPrincipal userPrincipal, @PathVariable @Schema(description = "책 id" , example = "1") Long bookId);
}
