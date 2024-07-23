package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.book.persistence.repository.BookRepository;
import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookCommandService {
    private final BookRepository bookRepository;
    public void saveBook(NaverBookDTO naverBookDTO) {
        Book book = Book.fromNaverBookDTO(naverBookDTO);
        bookRepository.save(book);
    }
}
