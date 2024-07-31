package com.example.OMEB.domain.event.application;

import java.util.concurrent.TimeUnit;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.OMEB.domain.event.persistence.entity.EventReview;
import com.example.OMEB.domain.event.persistence.entity.EventView;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScoreEventListener {
	private final RedisTemplate<String, EventReview> redisTemplateReview;
	private final RedisTemplate<String, EventView> redisTemplateView;

	@EventListener
	public void reviewEventListen(EventReview eventReview) {
		redisTemplateReview.opsForList().rightPush("event_review", eventReview);
		redisTemplateReview.expire("event_review", 60*60*24, TimeUnit.SECONDS); // 24시간 후 만료
	}

	@EventListener
	public void viewEventListen(EventView eventView) {
		redisTemplateView.opsForList().rightPush("event_view", eventView);
		redisTemplateView.expire("event_view", 60*60*24, TimeUnit.SECONDS); // 24시간 후 만료

	}
}
