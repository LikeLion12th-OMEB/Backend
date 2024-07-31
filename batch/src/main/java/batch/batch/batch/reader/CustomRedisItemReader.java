package batch.batch.batch.reader;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class CustomRedisItemReader<K,V> implements ItemStreamReader<List<V>> {
	private final RedisTemplate<K, V> redisTemplate;
	private final K key;
	private int index;
	private final int chunkSize;
	private static final String CURRENT_INDEX_KEY = "current.index";
	public CustomRedisItemReader(RedisTemplate<K, V> redisTemplate, K key, int chunkSize) {
		Assert.notNull(redisTemplate, "redisTemplate must not be null");
		Assert.notNull(key, "key must not be null");
		this.redisTemplate = redisTemplate;
		this.key = key;
		this.chunkSize = chunkSize;
		this.index = 0;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey(CURRENT_INDEX_KEY)) {
			this.index = executionContext.getInt(CURRENT_INDEX_KEY);
		} else {
			this.index = 0;
		}
	}

	@Override
	public List<V> read() throws Exception {
		List<V> items = redisTemplate.opsForList().range(key, index, index + chunkSize - 1);
		if (items != null && !items.isEmpty()) {
			index += chunkSize;
			return items;
		} else {
			return null;
		}
	}

	@Override
	public void close() throws ItemStreamException {
		// 리소스 해제 코드, 필요시 추가
	}
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putInt(CURRENT_INDEX_KEY, this.index);
	}
}
