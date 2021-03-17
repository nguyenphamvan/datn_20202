package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private String productName;
    private String SupplierName;
    private int quantity;
    private int price;
    private int discount;
    private int totalPrice;
    private boolean reviewed;
}
