package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orderDetails")
@ToString(exclude = "orderDetails")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactReceiver;
    private String contactPhone;
    private String contactAddress;
    private int subTotal;
    private int shipFee;
    private int discount;
    private int total;
    private String status;
    private String paymentMethod;
    private String message;
    private String note;
    @CreationTimestamp
    private Timestamp orderDate;
    @UpdateTimestamp
    private Timestamp deliveryDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLogging> orderLoggings;

    public void addOrderDetail(OrderDetail orderDetail) {
        if(this.orderDetails == null) {
            this.orderDetails = new HashSet<>();
        }
        this.orderDetails.add(orderDetail);
    }

    public void addLogOrder(OrderLogging logOrder) {
        if(this.orderLoggings == null) {
            this.orderLoggings = new HashSet<>();
        }
        this.orderLoggings.add(logOrder);
    }

    // should order moder mapper
    public OrderDto convertOrderToOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(this.getId());
        orderDto.setContactReceiver(this.getContactReceiver());
        orderDto.setContactPhone(this.getContactPhone());
        orderDto.setContactAddress(this.getContactAddress());
        orderDto.setSubTotal(this.getSubTotal());
        orderDto.setShipFee(this.getShipFee());
        orderDto.setDiscount(this.getDiscount());
        orderDto.setTotal(this.getTotal());
        orderDto.setStatus(this.getStatus());
        orderDto.setMessage(this.getMessage());
        orderDto.setPaymentMethod(this.getPaymentMethod());
        orderDto.setNote(this.getNote());
        orderDto.setOrderDate(this.getOrderDate());
        orderDto.setDeliveryDate(this.getDeliveryDate());
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        StringBuilder summaryProductName = new StringBuilder("");
        this.getOrderDetails().forEach(orderDetail -> {
            summaryProductName.append(orderDetail.getProduct().getProductName());
            orderDetailDtoList.add(orderDetail.convertOrderDetailToOrderDetailDto());
        });
        orderDto.setSummaryProductName(summaryProductName.toString());
        orderDto.setListOrderDetail(orderDetailDtoList);
        return orderDto;
    }

    public OrderLoggingDto convertOrderLoggingToOrderLoggingDto() {
        OrderLoggingDto orderLoggingDto = new OrderLoggingDto();
        orderLoggingDto.setOrderId(this.getId());
        orderLoggingDto.setLatestStatus(this.getStatus());
        orderLoggingDto.setLastUpdatedTime(this.getDeliveryDate());
        Set<OrderLoggingDto.LoggingOrderStatus> loggingStatus = this.getOrderLoggings().stream()
                .map(ol -> new OrderLoggingDto.LoggingOrderStatus(ol.getStatus(), ol.getUpdateTime()))
                .collect(Collectors.toSet());
        orderLoggingDto.setLoggingStatus(loggingStatus);
        return orderLoggingDto;
    }

}
