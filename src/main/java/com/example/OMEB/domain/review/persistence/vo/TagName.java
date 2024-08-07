package com.example.OMEB.domain.review.persistence.vo;

import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum TagName {

    DEPRESSION,
    ANGER,
    ANXIETY,
    LONELINESS,
    JEALOUSY,
    HAPPINESS,
    LETHARGY,
    LOVE,
    ACCOMPLISHMENT,
    ;

    // TODO : data binder 추후 추가하여 validation 진행 해야함
    @JsonCreator
    public static TagName fromString(String value) {
        for(TagName tagName : TagName.values()) {
            if(tagName.toString().equalsIgnoreCase(value)) {
                return tagName;
            }
        }
        throw new ServiceException(ErrorCode.INVALID_INPUT_TAG);
    }
}
