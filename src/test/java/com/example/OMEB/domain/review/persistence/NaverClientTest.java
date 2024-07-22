package com.example.OMEB.domain.review.persistence;

import com.example.OMEB.domain.book.application.service.Book;
import com.example.OMEB.domain.book.application.service.Emotion;
import com.example.OMEB.domain.book.application.service.NaverBookSearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NaverClientTest {
    @Autowired
    private NaverBookSearchClient client;

    @Test
    void 웹_클라이언트_테스트() {
        Emotion.values();
        for (Emotion emotion : Emotion.values()) {
            String response = client.searchBooks(emotion.getDescription(), null);
            if (response != null) {
                List<Book> books = client.parseBooks(response);
                System.out.println("emotion.getDescription() = " + emotion.getDescription());
                System.out.println("books.len = " + books.size());
            } else {
                System.out.println("Failed to retrieve data.");
            }
        }
    }

}
