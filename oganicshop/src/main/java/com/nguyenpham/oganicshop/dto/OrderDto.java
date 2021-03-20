package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String contactReceiver;
    private String contactPhone;
    private String contactAddress;
    private String summaryProductName;
    private int subTotal;
    private int shipFee;
    private int discount;
    private int total;
    private String message;
    private String status;
    private String paymentMethod;
    private String note;
    private Timestamp orderDate;
    private Timestamp deliveryDate;
    private List<OrderDetailDto> listOrderDetail;
}
