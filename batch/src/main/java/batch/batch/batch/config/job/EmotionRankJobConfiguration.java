package batch.batch.batch.config.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmotionRankJobConfiguration {
	private final JobRepository jobRepository;

	private final Step eventReviewToEventScoreStep;
	private final Step eventViewToEventScoreStep;
	private final Step eventScoreToBookScoreStep;
	private final Step bookScoreToEmotionRankStep;

	@Bean
	public Job emotionRankJob() {
		return new JobBuilder("emotionRankJob", jobRepository)
			.start(eventReviewToEventScoreStep)
			.next(eventViewToEventScoreStep)
			.next(eventScoreToBookScoreStep)
			.next(bookScoreToEmotionRankStep)
			.build();
	}
}
