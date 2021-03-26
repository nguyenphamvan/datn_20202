package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Long id;
    private String title;
    private String code;
    private double discountPercent;
    private int discountPrice;
    private int minOrderValue;
    private Date startDate;
    private Date endDate;
    private int numberOfUses;
}
