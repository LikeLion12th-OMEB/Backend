package batch.batch.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import batch.batch.rdb.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BookScoreToEmotionRankStepConfiguration {
	private final JobRepository jobRepository;
	private final BookRepository bookRepository;
	private final PlatformTransactionManager platformTransactionManager;

	@Bean
	public Step bookScoreToEmotionRankStep(MongoTemplate mongoTemplate) {
		return new StepBuilder("emotionRankStep", jobRepository)
			.tasklet(new EmotionRankTasklet(mongoTemplate,bookRepository),platformTransactionManager)
			.build();
	}
}
