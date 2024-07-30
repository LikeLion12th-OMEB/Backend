package batch.batch.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventReview {
	private Long userId;
	private Long bookId;
	private String emotion;
	private String time;

	public EventReview(Long userId, Long bookId, String emotion, String time) {
		this.userId = userId;
		this.bookId = bookId;
		this.emotion = emotion;
		this.time = time;
	}
}
