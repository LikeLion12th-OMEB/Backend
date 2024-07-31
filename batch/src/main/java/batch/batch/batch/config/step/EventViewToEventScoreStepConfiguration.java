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
import batch.batch.redis.entity.EventScore;
import batch.batch.redis.entity.EventView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EventViewToEventScoreStepConfiguration {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	private final RedisTemplate<String, EventView> redisTemplateView;
	private final CustomRedisItemWriter<String, EventScore> eventScoreWriter;

	@Bean
	public Step eventViewToEventScoreStep() {
		return new StepBuilder("eventViewToEventScoreStep", jobRepository)
			.<List<EventView>, List<EventScore>> chunk(10, platformTransactionManager)
			.reader(eventViewReader())
			.processor(viewToEventScoreProcessor())
			.writer(eventScoreWriter)
			.build();
	}
	@Bean
	public CustomRedisItemReader<String, EventView> eventViewReader() {
		return new CustomRedisItemReader<String, EventView>(redisTemplateView, "event_view", 10);
	}

	@Bean
	public ItemProcessor<List<EventView>, List<EventScore>> viewToEventScoreProcessor() {
		return new ItemProcessor<List<EventView>, List<EventScore>>() {
			@Override
			public List<EventScore> process(List<EventView> views) throws Exception {
				List<EventScore> list = views.stream().map(EventScore::new).toList();
				return list;
			}
		};
	}
}
