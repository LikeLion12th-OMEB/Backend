package batch.batch.rdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import batch.batch.rdb.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

