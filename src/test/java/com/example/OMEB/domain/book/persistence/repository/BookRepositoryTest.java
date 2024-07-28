package com.example.OMEB.domain.book.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.OMEB.domain.book.persistence.entity.Book;
import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.oauth.user.OAuth2Provider;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void test() {

	}

}