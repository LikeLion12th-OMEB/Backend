package batch.batch.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class CustomRedisScoreReader <K,V> implements ItemStreamReader<List<V>> {
	private final RedisTemplate<K, V> redisTemplate;
	private final String pattern;
	private final int chunkSize;
	private Set<K> keys;
	private int currentIndex;
	private static final String CURRENT_INDEX_KEY = "current.index";
	public CustomRedisScoreReader(RedisTemplate<K, V> redisTemplate, String pattern, int chunkSize) {
		Assert.notNull(redisTemplate, "redisTemplate must not be null");
		Assert.notNull(pattern, "pattern must not be null");
		this.redisTemplate = redisTemplate;
		this.pattern = pattern;
		this.chunkSize = chunkSize;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.keys = redisTemplate.keys((K) pattern);
		if (executionContext.containsKey(CURRENT_INDEX_KEY)) {
			this.currentIndex = executionContext.getInt(CURRENT_INDEX_KEY);
		} else {
			this.currentIndex = 0;
		}
	}

	@Override
	public List<V> read() throws Exception {
		if (keys == null || keys.isEmpty() || currentIndex >= keys.size()) {
			return null;
		}

		List<V> items = new ArrayList<>();
		int endIndex = Math.min(currentIndex + chunkSize, keys.size());
		List<K> keyList = new ArrayList<>(keys);

		for (int i = currentIndex; i < endIndex; i++) {
			K key = keyList.get(i);
			V value = redisTemplate.opsForValue().get(key);
			if (value != null) {
				items.add(value);
			}
		}

		currentIndex += chunkSize;
		return items;
	}

	@Override
	public void close() throws ItemStreamException {
		keys = null;
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putInt("currentIndex", currentIndex);
	}
}
