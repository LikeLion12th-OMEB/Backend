package com.example.OMEB.domain.book.persistence.repository;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);

	// TODO : [대회 후TODO] 걍 대충 구현함 나중에 수정해야함
    @Query(value = "SELECT b.* FROM book b LEFT JOIN review r ON r.book_id = b.id GROUP BY b.id ORDER BY COUNT(r.id) DESC LIMIT 10", nativeQuery = true)
    List<Book> findTopBooksByReviewRank();

	// TODO : [대회 후TODO] 걍 대충 구현함 나중에 수정해야함
	@Query("SELECT new com.example.OMEB.domain.book.presentation.dto.response.BookTitleInfoResponse(b.id, b.title, b.author, b.bookImage, b.price) "
		+ "FROM Book b "
		+ "WHERE b.title LIKE CONCAT('%', :title, '%')")
	Page<BookTitleInfoResponse> findByTitleContaining(String title, Pageable pageable);
}
