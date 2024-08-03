package com.example.OMEB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OmebApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmebApplication.class, args);
	}

}
