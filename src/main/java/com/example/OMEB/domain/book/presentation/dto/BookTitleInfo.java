package com.example.OMEB.domain.book.presentation.dto;

import com.example.OMEB.domain.book.persistence.entity.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "BookTitleInfo", description = "책 제목 정보")
public class BookTitleInfo {
	@Schema(description = "책 id", example = "1")
	private Long bookId;
	@Schema(description = "책 제목", example = "책 제목")
	private String title;
	@Schema(description = "책 이미지 url", example = "http://image.com")
	private String imageUrl;
	@Schema(description = "책 가격", example = "10000")
	private double price;
	@Schema(description = "책 저자", example = "책 저자")
	private String author;


	public BookTitleInfo(Long bookId, String title, String imageUrl, double price, String author) {
		this.bookId = bookId;
		this.title = title;
		this.imageUrl = imageUrl;
		this.price = price;
		this.author = author;
	}

	public static BookTitleInfo of(Book book) {
		return new BookTitleInfo(book.getId(), book.getTitle(), book.getBookImage(), Double.parseDouble(book.getPrice()), book.getAuthor());
	}
}
