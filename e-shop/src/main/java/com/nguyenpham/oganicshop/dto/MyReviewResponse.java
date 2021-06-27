package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyReviewResponse implements Comparable<MyReviewResponse>{

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp updatedAt;

    @Override
    public int compareTo(MyReviewResponse rv) {
        return this.getCreatedAt().compareTo(rv.getCreatedAt());
    }
}
