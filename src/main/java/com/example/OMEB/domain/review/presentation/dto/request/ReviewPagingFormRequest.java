package com.example.OMEB.domain.review.presentation.dto.request;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPagingFormRequest {
    @Builder.Default
    int pageOffset=1;
    @Builder.Default
    int pageSize=10;
    @Builder.Default
    String sort="createdAt";
}
