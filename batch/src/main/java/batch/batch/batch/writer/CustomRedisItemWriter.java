package batch.batch.batch.writer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import batch.batch.redis.entity.EventScore;

public class CustomRedisItemWriter<K, V> implements ItemWriter<List<V>> {
	private static final Logger log = LoggerFactory.getLogger(CustomRedisItemWriter.class);
	private final RedisTemplate<K, V> redisTemplate;
	private final long ttl; // TTL in seconds

	public CustomRedisItemWriter(RedisTemplate<K, V> redisTemplate, long ttl) {
		this.redisTemplate = redisTemplate;
		this.ttl = ttl;
	}

	@Override
	public void write(Chunk<? extends List<V>> chunk) throws Exception {
		ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
		for (List<V> itemList : chunk.getItems()) {
			for (V item : itemList) {
				if (item instanceof EventScore) {
					EventScore eventScore = (EventScore) item;
					String key = "event_score_" + eventScore.getBookId();
					EventScore existingScore = (EventScore) valueOperations.get((K) key);
					if (existingScore != null) {
						eventScore.setDepression(eventScore.getDepression() + existingScore.getDepression());
						eventScore.setAnger(eventScore.getAnger() + existingScore.getAnger());
						eventScore.setAnxiety(eventScore.getAnxiety() + existingScore.getAnxiety());
						eventScore.setLoneliness(eventScore.getLoneliness() + existingScore.getLoneliness());
						eventScore.setJealousy(eventScore.getJealousy() + existingScore.getJealousy());
						eventScore.setHappiness(eventScore.getHappiness() + existingScore.getHappiness());
						eventScore.setLethargy(eventScore.getLethargy() + existingScore.getLethargy());
						eventScore.setLove(eventScore.getLove() + existingScore.getLove());
						eventScore.setAccomplishment(eventScore.getAccomplishment() + existingScore.getAccomplishment());
					}
					valueOperations.set((K) key, (V) eventScore, ttl, TimeUnit.SECONDS);
				}

			}
		}
	}
}