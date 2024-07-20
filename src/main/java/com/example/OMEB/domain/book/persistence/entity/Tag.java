package com.example.OMEB.domain.book.persistence.entity;

import com.example.OMEB.domain.review.persistence.entity.Review;
import com.example.OMEB.domain.user.persistence.entity.User;
import com.example.OMEB.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tag_name;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Review> reviews = new ArrayList<>();
}
