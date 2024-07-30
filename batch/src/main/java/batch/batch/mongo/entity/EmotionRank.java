package batch.batch.mongo.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "emotion_rank")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmotionRank {
	@Id
	private String id;
	private String emotion;
	private List<BookTitleInfo> bookTitleInfos;

}
