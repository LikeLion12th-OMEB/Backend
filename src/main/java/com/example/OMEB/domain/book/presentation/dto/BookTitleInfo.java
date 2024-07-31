package com.example.OMEB.domain.book.presentation.dto;

import com.example.OMEB.domain.book.persistence.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookTitleInfo {
	private Long bookId;
	private String title;
	private String imageUrl;
	private double price;
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
