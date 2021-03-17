package com.nguyenpham.oganicshop.dto;

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
public class RequestReviewDto {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long rootId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long productId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    private String comment;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int ratting;
    private Date createdAt;
    private Date updatedAt;
}
