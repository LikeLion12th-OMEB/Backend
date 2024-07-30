package batch.batch.rdb.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String isbn;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String publisher;

	@Column(nullable = false,name = "publication_date")
	private String publicationDate;

	@Column(nullable = false,name = "book_image")
	private String bookImage;

	@Column(nullable = false)
	private String price;

	@Column(nullable = false,name = "sell_link")
	private String sellLink;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Book(String title, String isbn, String description, String author, String publisher, String publicationDate,
		String bookImage, String price, String sellLink, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.title = title;
		this.isbn = isbn;
		this.description = description;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.bookImage = bookImage;
		this.price = price;
		this.sellLink = sellLink;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
