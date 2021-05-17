package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderResponse {

    private Long id;
    private AddressRequest address;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private String orderDate;
    private String deliveryDate;
    private List<OrderItemDto> listOrderDetail;
    private OrderLoggingDto orderLogging;
}
