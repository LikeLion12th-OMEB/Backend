package com.example.OMEB.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.OMEB.global.event.persistence.entity.EventReview;
import com.example.OMEB.global.event.persistence.entity.EventView;

@Configuration
public class EventRedisConfig {

	@Value("${spring.batch.redis.host}")
	private String host;

	@Value("${spring.batch.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory eventRedisConnectionFactory() { // Lettuce 와 Judis 가 있는데 요즘은 Lettuce 가 더 많이 사용되는거 같음
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public RedisTemplate<String, EventReview> redisTemplateReview(RedisConnectionFactory eventRedisConnectionFactory){
		// GenericJackson2JsonRedisSerializer 설정
		RedisTemplate<String, EventReview> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(eventRedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(EventReview.class));  // Value: 직렬화에 사용할 Object 사용하기
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, EventView> redisTemplateView(RedisConnectionFactory eventRedisConnectionFactory){
		// GenericJackson2JsonRedisSerializer 설정
		RedisTemplate<String, EventView> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(eventRedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(EventView.class));  // Value: 직렬화에 사용할 Object 사용하기
		return redisTemplate;
	}
}
