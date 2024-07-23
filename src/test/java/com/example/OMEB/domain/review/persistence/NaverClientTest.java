package com.example.OMEB.domain.review.persistence;

import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
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
            List<NaverBookDTO> naverBookDTOS = client.searchBooks(emotion.getDescription(), null);
            System.out.println("emotion.getDescription() = " + emotion.getDescription());
            System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
        }
    }

    @Test
    void 책_클라이언트_에러() {
        List<NaverBookDTO> naverBookDTOS = client.searchBooks("qnpfr", null);
        System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
    }

}
