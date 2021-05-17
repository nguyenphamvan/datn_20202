package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReviewResponse {

    private Long id;
    private Long idRootReview;
    private Long userId;
    private Long productId;
    private String productImg;
    private String reviewerName;
    private String title;
    private String comment;
    private int rating;
    private int numbersOfLike;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    private Set<ReviewResponse> subReviews;
    public void addSubReview(ReviewResponse subReview) {
        if (this.subReviews == null) {
            this.subReviews = new HashSet<>();
        }
        this.subReviews.add(subReview);
    }


}
