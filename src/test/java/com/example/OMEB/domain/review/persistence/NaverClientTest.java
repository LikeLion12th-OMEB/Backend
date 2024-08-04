package com.example.OMEB.domain.review.persistence;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NaverClientTest {
    // private static final Logger log = LoggerFactory.getLogger(NaverClientTest.class);
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
    //
    //     // 감정 키워드와 해당 데이터 범위 설정
    //     Map<String, Integer[]> keywordRanges = Map.of(
    //         "DEPRESSION", new Integer[] {0, 94},   // 예시: 95권
    //         "ANGER", new Integer[] {95, 194},  // 예시: 100권
    //         "ANXIETY", new Integer[] {195, 294}, // 예시: 100권
    //         "LONELINESS", new Integer[] {295, 394}, // 예시: 100권
    //         "JEALOUSY", new Integer[] {395, 461}, // 예시: 67권
    //         "HAPPINESS", new Integer[] {462, 561}, // 예시: 100권
    //         "LETHARGY", new Integer[] {562, 594}, // 예시: 33권
    //         "LOVE", new Integer[] {595, 694}, // 예시: 100권
    //         "ACCOMPLISHMENT", new Integer[] {695, 794} // 예시: 100권
    //     );
    //
    //     // 각 감정 키워드에 대해 30개의 데이터만 처리
    //     for (Map.Entry<String, Integer[]> entry : keywordRanges.entrySet()) {
    //         String keyword = entry.getKey();
    //         Integer[] range = entry.getValue();
    //
    //         int startRange = range[0];
    //         int endRange = range[1];
    //
    //         // 데이터 수를 초과하지 않도록 조정
    //         int limit = Math.min(30, endRange - startRange + 1);
    //
    //         for (int i = startRange; i < startRange + limit; i++) {
    //             if (i > endRange) break;
    //             Book book = bookRepository.findById((long)(start + i)).orElse(null);
    //             if (book != null) {
    //                 log.info("book = {}", book);
    //                 publisher.publishEvent(new EventReview(1L, book.getId(), keyword, "2023-01-01"));
    //             }
    //         }
    //     }
    // }
    //
    // @Test
    // void 책_클라이언트_에러() {
    //     //        List<NaverBookDTO> naverBookDTOS = client.searchBooks("qnpfr", null);
    //     //        System.out.println("bookDTOS.size() = " + naverBookDTOS.size());
    // }

}
