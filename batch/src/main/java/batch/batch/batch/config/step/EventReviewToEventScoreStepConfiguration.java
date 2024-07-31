package batch.batch.batch.config.step;

import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import batch.batch.batch.reader.CustomRedisItemReader;
import batch.batch.batch.writer.CustomRedisItemWriter;
import batch.batch.redis.entity.EventReview;
import batch.batch.redis.entity.EventScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EventReviewToEventScoreStepConfiguration {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	private final RedisTemplate<String, EventReview> redisTemplateReview;
	private final RedisTemplate<String, EventScore> redisTemplateScore;


	@Bean
	public Step eventReviewToEventScoreStep() {
		return new StepBuilder("eventReviewToEventScoreStep", jobRepository)
			.<List<EventReview>, List<EventScore>> chunk(10, platformTransactionManager)
			.reader(eventReviewReader())
			.processor(reviewToEventScoreProcessor())
			.writer(eventScoreWriter())
			.build();
	}

	@Bean
	public CustomRedisItemReader<String, EventReview> eventReviewReader() {
		return new CustomRedisItemReader<String,EventReview>(redisTemplateReview, "event_review", 10);
	}

	@Bean
	public ItemProcessor<List<EventReview>, List<EventScore>> reviewToEventScoreProcessor() {

		return new ItemProcessor<List<EventReview>,  List<EventScore>>() {
			@Override
			public  List<EventScore> process(List<EventReview> reviews) throws Exception {
				List<EventScore> list = reviews.stream().map(EventScore::new).toList();
				return list;
			}
		};
	}

	@Bean
	public CustomRedisItemWriter<String, EventScore> eventScoreWriter() {
		return new CustomRedisItemWriter<>(redisTemplateScore, 7200); // TTL 2시간 (7200초)
	}
}
