package com.example.OMEB.domain.book.application.service;

public enum Emotion {
    SADNESS("우울"),
    ANGER("분노"),
    ANXIETY("불안"),
    LONELINESS("외로움"),
    JEALOUSY("질투"),
    HAPPINESS("행복"),
    LETHARGY("무기력"),
    LOVE("사랑"),
    SENSE_OF_ACHIEVEMENT("성취감"),
    OTHER("기타"); // 사용자에게 보이지 않도록 할 수 있습니다.

    private final String description;

    Emotion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}
