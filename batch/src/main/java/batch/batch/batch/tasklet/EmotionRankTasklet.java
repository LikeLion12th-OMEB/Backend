package batch.batch.batch.tasklet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import batch.batch.mongo.entity.BookScore;
import batch.batch.mongo.entity.BookTitleInfo;
import batch.batch.mongo.entity.EmotionRank;
import batch.batch.rdb.entity.Book;
import batch.batch.rdb.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmotionRankTasklet implements Tasklet {
	private final MongoTemplate mongoTemplate;
	private final BookRepository bookRepository;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		updateEmotionRanks();
		return RepeatStatus.FINISHED;
	}


	private void updateEmotionRanks() {
		// 기존 EmotionRank 데이터 삭제
		mongoTemplate.remove(new Query(), EmotionRank.class);

		// 각 감정별 상위 30권 책 선정
		String[] emotions = {"depression", "anger", "anxiety", "loneliness", "jealousy", "happiness", "lethargy", "love", "accomplishment"};

		for (String emotion : emotions) {
			Query query = new Query();
			query.with(Sort.by(Sort.Order.desc(emotion)));
			query.limit(30);

			List<BookScore> topBooks = mongoTemplate.find(query, BookScore.class);

			List<BookTitleInfo> bookTitleInfos = topBooks.stream()
				.map(bookScore -> {
					Book book = bookRepository.findById(bookScore.getBookId()).orElse(null);
					return book != null ? BookTitleInfo.of(book) : null;
				})
				.filter(bookTitleInfo -> bookTitleInfo != null)
				.collect(Collectors.toList());

			EmotionRank emotionRank = new EmotionRank();
			emotionRank.setEmotion(emotion);
			emotionRank.setBookTitleInfos(bookTitleInfos);

			mongoTemplate.save(emotionRank);
		}
	}
}
