package batch.batch.mongo.reository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import batch.batch.mongo.entity.BookScore;

@Repository
public interface BookScoreRepository extends MongoRepository<BookScore, String> {
	BookScore findByBookId(Long bookId, Class<BookScore> bookScoreClass);
}
