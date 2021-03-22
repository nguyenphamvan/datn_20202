package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseReviewDto {

    private Long id;
    private Long idRootReview;
    private Long userId;
    private Long productId;
    private String reviewerName;
    private String title;
    private String comment;
    private String img;
    private int rating;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private Set<ResponseReviewDto> subReviews;
    public void addSubReview(ResponseReviewDto subReview) {
        if (this.subReviews == null) {
            this.subReviews = new HashSet<>();
        }
        this.subReviews.add(subReview);
    }


}
