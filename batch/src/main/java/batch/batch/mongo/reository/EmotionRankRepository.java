package batch.batch.mongo.reository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import batch.batch.mongo.entity.EmotionRank;

@Repository
public interface EmotionRankRepository extends MongoRepository<EmotionRank, String> {
	EmotionRank findByEmotion(String emotion);
}
