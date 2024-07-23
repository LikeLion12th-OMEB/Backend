package com.example.OMEB.domain.book.presentation.dto;

import lombok.Getter;

@Getter
public class NaverBookDTO {
    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String isbn;
    private String description;
    private String pubdate;

    public NaverBookDTO(String title, String link, String image, String author, String discount, String publisher, String isbn, String description, String pubdate) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.discount = discount;
        this.publisher = publisher;
        this.isbn = isbn;
        this.description = description;
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", discount='" + discount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", pubdate='" + pubdate + '\'' +
                '}';
    }
}