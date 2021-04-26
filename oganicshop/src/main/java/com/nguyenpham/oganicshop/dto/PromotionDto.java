package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PromotionDto {

    private Long id;
    private String title;
    private String code;
    private double discountPercent;
    private int discountPrice;
    private int minOrderValue;
    private int maxDiscountValue;
    private Date startDate;
    private Date endDate;
    private int numberOfUses;
}
