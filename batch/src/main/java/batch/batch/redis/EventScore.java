package batch.batch.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class EventScore {
	private Long bookId;
	private int depression;
	private int anger;
	private int anxiety;
	private int loneliness;
	private int jealousy;
	private int happiness;
	private int lethargy;
	private int love;
	private int accomplishment;

	public EventScore(Long bookId) {
		this.bookId = bookId;
		this.depression = 0;
		this.anger = 0;
		this.anxiety = 0;
		this.loneliness = 0;
		this.jealousy = 0;
		this.happiness = 0;
		this.lethargy = 0;
		this.love = 0;
		this.accomplishment = 0;
	}

	public EventScore(EventReview review) {
		this.bookId = review.getBookId();
		this.depression = review.getEmotion().equals("DEPRESSION") ? 10: 0;
		this.anger = review.getEmotion().equals("ANGER") ? 10: 0;
		this.anxiety = review.getEmotion().equals("ANXIETY") ? 10: 0;
		this.loneliness = review.getEmotion().equals("LONELINESS") ? 10: 0;
		this.jealousy = review.getEmotion().equals("JEALOUSY") ? 10: 0;
		this.happiness = review.getEmotion().equals("HAPPINESS") ? 10: 0;
		this.lethargy = review.getEmotion().equals("LETHARGY") ? 10: 0;
		this.love = review.getEmotion().equals("LOVE") ? 10: 0;
		this.accomplishment = review.getEmotion().equals("ACCOMPLISHMENT") ? 10: 0;
	}

	public EventScore(EventView view) {
		int score = 1;
		this.bookId = view.getBookId();
		this.depression = score;
		this.anger = score;
		this.anxiety = score;
		this.loneliness = score;
		this.jealousy = score;
		this.happiness = score;
		this.lethargy = score;
		this.love = score;
		this.accomplishment = score;
	}
}
