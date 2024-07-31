package com.example.OMEB.domain.book.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.OMEB.domain.book.persistence.entity.EmotionRank;

@Repository
public interface EmotionRankRepository extends MongoRepository<EmotionRank, String> {
	EmotionRank findByEmotion(String emotion);
}
