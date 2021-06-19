package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubReviewResponse {
    private long id;
    private long reviewerId;
    private String reviewerName;
    private String content;
    private String createdDate;
    private long rootReviewId;
}
