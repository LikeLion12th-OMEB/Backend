package com.example.OMEB.domain.event.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EventView {
	private Long userId;
	private Long bookId;
	private String time;

	public EventView(Long userId, Long bookId,  String time) {
		this.userId = userId;
		this.bookId = bookId;
		this.time = time;
	}
}
