package com.example.OMEB.domain.book.persistence.repository;

import com.example.OMEB.domain.book.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
