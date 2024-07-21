package com.example.OMEB.domain.review.presentation.dto.request;

import com.example.OMEB.domain.review.persistence.vo.TagName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewUpdateRequest {
    private String content;
    private TagName tag;
}
