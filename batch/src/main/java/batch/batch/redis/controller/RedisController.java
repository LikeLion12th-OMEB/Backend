package batch.batch.redis.controller;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import batch.batch.redis.entity.EventReview;
import batch.batch.redis.entity.EventView;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedisController {

	private final RedisTemplate<String, String> redisTemplate;
	private final RedisTemplate<String, EventReview> redisTemplateReview;
	private final RedisTemplate<String, EventView> redisTemplateView;

	@GetMapping("/redis/test")
	public String test() {
		ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
		stringStringValueOperations.set("test", "test");

		return "test";
	}

	@GetMapping("/redis/test2")
	public String test2() {
		EventReview happy = new EventReview(1L, 1L, "HAPPINESS", "2021-07-01");
		EventReview sad = new EventReview(2L, 2L, "SADNESS", "2021-07-02");
		EventReview angry = new EventReview(3L, 3L, "ANGER", "2021-07-03");
		EventReview jealous = new EventReview(4L, 4L, "JEALOUSY", "2021-07-04");
		redisTemplateReview.opsForList().rightPush("event_review", happy);
		redisTemplateReview.opsForList().rightPush("event_review", sad);
		redisTemplateReview.opsForList().rightPush("event_review", angry);
		redisTemplateReview.opsForList().rightPush("event_review", jealous);

		return "test";
	}

	@GetMapping("/redis/test3")
	public List<EventReview> test3() {
		List<EventReview> eventReview = redisTemplateReview.opsForList().range("event_review", 0, -1);
		return eventReview;
	}

	@GetMapping("/redis/test4")
	public String test4() {
		EventView eventView = new EventView(1L, 1L, "2021-07-01");
		EventView eventView1 = new EventView(2L, 2L, "2021-07-02");
		EventView eventView2 = new EventView(3L, 3L, "2021-07-03");

		redisTemplateView.opsForList().rightPush("event_view", eventView);
		redisTemplateView.opsForList().rightPush("event_view", eventView1);
		redisTemplateView.opsForList().rightPush("event_view", eventView2);

		return "test";
	}

	@GetMapping("/redis/test5")
	public List<EventView> test5() {
		List<EventView> eventView = redisTemplateView.opsForList().range("event_view", 0, -1);
		return eventView;
	}

	@GetMapping("/redis/test6")
	public String test6() {
		redisTemplate.delete("event_view");
		return "test";
	}

	@GetMapping("/redis/test7")
	public String test7() {
		redisTemplate.delete("event_review");
		return "test";
	}
}
