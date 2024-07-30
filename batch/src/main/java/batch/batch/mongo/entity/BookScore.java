package batch.batch.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "book_score")
@NoArgsConstructor
@Setter
@Getter
public class BookScore {
	@Id
	private String id;
	private Long bookId;
	private double depression;
	private double anger;
	private double anxiety;
	private double loneliness;
	private double jealousy;
	private double happiness;
	private double lethargy;
	private double love;
	private double accomplishment;

	public BookScore(Long bookId, double depression, double anger, double anxiety, double loneliness, double jealousy,
		double happiness, double lethargy, double love, double accomplishment) {
		this.bookId = bookId;
		this.depression = depression;
		this.anger = anger;
		this.anxiety = anxiety;
		this.loneliness = loneliness;
		this.jealousy = jealousy;
		this.happiness = happiness;
		this.lethargy = lethargy;
		this.love = love;
		this.accomplishment = accomplishment;
	}
}
