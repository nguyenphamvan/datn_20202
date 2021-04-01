package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoRequest {
    private Long id;
    private ShippingAddressDto shippingAddress;
    private String summaryProductName;
    private int subTotal;
    private int shipFee;
    private int discount;
    private int total;
    private String message;
    private String status;
    private String deliveryMethod;
    private String paymentMethod;
    private String note;
}
