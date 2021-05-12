package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private String productUrl;
    private String image;
    private String SupplierName;
    private int quantity;
    private int price;
    private int discount;
    private int rawTotal;
    private boolean reviewed;
}
