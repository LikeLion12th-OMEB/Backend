package com.example.OMEB.domain.review.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class ReviewPageResponse  {

    private int pageOffset;
    private int pageSize;
    private int totalPage;
    private List<ReviewInfoResponse> reviewInfoResponseList;

    public ReviewPageResponse(Page<ReviewInfoResponse> reviewInfoResponsePage) {
        this.pageOffset = reviewInfoResponsePage.getNumber();
        this.pageSize = reviewInfoResponsePage.getContent().size();
        this.totalPage = reviewInfoResponsePage.getTotalPages();
        this.reviewInfoResponseList = reviewInfoResponsePage.getContent();
    }
}
