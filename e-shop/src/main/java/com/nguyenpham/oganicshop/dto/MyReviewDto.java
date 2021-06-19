package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyReviewDto {

    private Long id;
    private Long idRootReview;
    private Long userId;
    private String productUrl;
    private String productName;
    private String productImg;
    private String reviewerName;
    private String title;
    private String comment;
    private int rating;
    private String createdAt;
    private String updatedAt;
}