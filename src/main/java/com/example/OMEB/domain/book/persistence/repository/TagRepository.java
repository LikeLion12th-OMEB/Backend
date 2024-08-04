package com.example.OMEB.domain.book.persistence.repository;

import com.example.OMEB.domain.review.persistence.entity.Tag;
import com.example.OMEB.domain.review.persistence.vo.TagName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByTagName(TagName tagName);
}
