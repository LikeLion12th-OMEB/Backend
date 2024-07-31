package batch.batch.batch.writer;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import batch.batch.mongo.entity.BookScore;
import batch.batch.mongo.reository.BookScoreRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMongoBookScoreWriter implements ItemWriter<List<BookScore>> {
	private final BookScoreRepository bookScoreRepository;

	@Override
	public void write(Chunk<? extends List<BookScore>> chunk) throws Exception {
		for (List<BookScore> bookScore : chunk) {
			//upsert
			bookScoreRepository.saveAll(bookScore);
		}
	}
}
