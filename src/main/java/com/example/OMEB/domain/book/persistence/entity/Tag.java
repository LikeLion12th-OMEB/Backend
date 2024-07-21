package com.example.OMEB.domain.book.persistence.entity;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@Getter
@Setter
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagName tagName;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Tag(TagName tag_name,Book book , Review review) {
        this.tagName = tag_name;
        this.reviews.add(review);
        this.books.add(book);
    }
}
