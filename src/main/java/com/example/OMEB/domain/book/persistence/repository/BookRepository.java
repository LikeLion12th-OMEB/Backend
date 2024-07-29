package com.example.OMEB.domain.book.persistence.repository;

import com.example.OMEB.domain.book.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);

    @Query(value = "SELECT b.* FROM book b LEFT JOIN review r ON r.book_id = b.id GROUP BY b.id ORDER BY COUNT(r.id) DESC LIMIT 10", nativeQuery = true)
    List<Book> findTopBooksByReviewRank();
}
