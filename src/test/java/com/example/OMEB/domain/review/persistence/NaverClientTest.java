package com.example.OMEB.domain.review.persistence;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.domain.book.application.service.NaverBookSearchClient;
import com.example.OMEB.domain.event.persistence.entity.EventReview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NaverClientTest {
    // @Autowired
    // private NaverBookSearchClient client;
    //
    // @Autowired
    // private BookRepository bookRepository;
    //
    // private String[] emotionList = {"우울", "분노", "불안", "외로움", "질투", "행복", "무기력", "사랑", "성취감"};
    //
    // @Autowired
    // private ApplicationEventPublisher publisher;
    //
    // @Test
    // void 웹_클라이언트_테스트() {
    //
    //     for (String emotionSearchWord : emotionList) {
    //         List<NaverBookDTO> naverBookDTOS = client.searchBooks(emotionSearchWord, null);
    //         System.out.println("emotion.getDescription() = " + emotionSearchWord);
    //         System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
    //         if(naverBookDTOS.size()!=2){
    //             System.out.println("사이즈가 ."+naverBookDTOS.size()+"입니다.");
    //             int saveNum = 0;
    //             for (NaverBookDTO naverBookDTO : naverBookDTOS) {
    //                 if (naverBookDTO.getIsbn() == null) {
    //                     continue;
    //                 }
    //                 Book book = Book.fromNaverBookDTO(naverBookDTO);
    //                 bookRepository.save(book);
    //                 saveNum++;
    //             }
    //             System.out.println(emotionSearchWord+":  저장된 책의 수는 "+saveNum+"입니다.");
    //
    //         }
    //     }
    // }
    //
    // @Test
    // void 적은_키워드_데이터_저장() {
    //     String keyword = "성취";
    //     List<NaverBookDTO> naverBookDTOS = client.searchBooks(keyword, null);
    //     System.out.println("emotion.getDescription() = " + keyword);
    //     System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
    //     if(naverBookDTOS.size()==100){
    //         for (NaverBookDTO naverBookDTO : naverBookDTOS) {
    //             if (naverBookDTO.getIsbn() == null) {
    //                 continue;
    //             }
    //             Book book = Book.fromNaverBookDTO(naverBookDTO);
    //             bookRepository.save(book);
    //         }
    //     }
    // }
    //
    // @Test
    // void Book_랭크() {
    //     int start = 1232;
    //     int size = 795;
    //     for (int i = 0; i < size; i++) {
    //         if (i > 696 && i <= 726) {
    //             Book book = bookRepository.findById((long)(start + i)).get();
    //             publisher.publishEvent(new EventReview(1L, book.getId(), "ACCOMPLISHMENT","2023-01-01"));
    //         }
    //     }
    // }

    @Test
    void 책_클라이언트_에러() {
        //        List<NaverBookDTO> naverBookDTOS = client.searchBooks("qnpfr", null);
        //        System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
    }

}
