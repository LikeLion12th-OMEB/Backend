package batch.batch.batch.config.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import batch.batch.batch.reader.CustomRedisScoreReader;
import batch.batch.batch.writer.CustomMongoBookScoreWriter;
import batch.batch.mongo.entity.BookScore;
import batch.batch.mongo.reository.BookScoreRepository;
import batch.batch.redis.entity.EventScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EventScoreToBookScoreStepConfiguration {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final RedisTemplate<String, EventScore> redisTemplateScore;
	private final BookScoreRepository bookScoreRepository;

	private static final float GAMMA = 0.5f;

	@Bean
	public Step eventScoreToBookScoreStep() {
		return new StepBuilder("eventScoreToBookScoreStep", jobRepository)
			.<List<EventScore>, List<BookScore>> chunk(10, platformTransactionManager)
			.reader(eventScoreReader())
			.processor(eventScoreToBookScoreProcessor())
			.writer(bookScoreWriter())
			.build();
	}

	@Bean
	public CustomRedisScoreReader<String, EventScore> eventScoreReader() {
		return new CustomRedisScoreReader<String,EventScore>(redisTemplateScore, "event_score_*", 10);
	}

	@Bean
	public ItemProcessor<List<EventScore>, List<BookScore>> eventScoreToBookScoreProcessor() {
		return new ItemProcessor<List<EventScore>, List<BookScore>>() {
			@Override
			public List<BookScore> process(List<EventScore> eventScores) throws Exception {
				List<BookScore> bookScores = new ArrayList<>();
				for (EventScore eventScore : eventScores) {
					if (eventScore instanceof EventScore) {
						bookScores.add(convertToBookScore(eventScore));
					} else {
						log.error("[EventScoreToBookScoreProcessor] [process] : item is not instance of EventScore");
						return null;
					}
				}
				return bookScores;
			}
		};
	}

	@Bean
	public ItemWriter<List<BookScore>> bookScoreWriter() {
		return new CustomMongoBookScoreWriter(bookScoreRepository);
	}

	private BookScore convertToBookScore(EventScore eventScore) {

		BookScore bookScore = bookScoreRepository.findByBookId(eventScore.getBookId(), BookScore.class);
		if (bookScore == null) {
			bookScore = new BookScore(eventScore.getBookId(), 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}
		bookScore.setDepression(bookScore.getDepression()*GAMMA + eventScore.getDepression());
		bookScore.setAnger(bookScore.getAnger()*GAMMA + eventScore.getAnger());
		bookScore.setAnxiety(bookScore.getAnxiety()*GAMMA + eventScore.getAnxiety());
		bookScore.setLoneliness(bookScore.getLoneliness()*GAMMA + eventScore.getLoneliness());
		bookScore.setJealousy(bookScore.getJealousy()*GAMMA + eventScore.getJealousy());
		bookScore.setHappiness(bookScore.getHappiness()*GAMMA + eventScore.getHappiness());
		bookScore.setLethargy(bookScore.getLethargy() *GAMMA+ eventScore.getLethargy());
		bookScore.setLove(bookScore.getLove()*GAMMA + eventScore.getLove());
		bookScore.setAccomplishment(bookScore.getAccomplishment()*GAMMA + eventScore.getAccomplishment());
		return bookScore;
	}
}
