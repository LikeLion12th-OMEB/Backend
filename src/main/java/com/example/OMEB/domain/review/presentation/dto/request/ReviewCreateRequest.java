package com.example.OMEB.domain.review.presentation.dto.request;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
    private String content;
    private TagName tag;
}
