package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import com.nguyenpham.oganicshop.dto.ShippingAddressDto;
import com.nguyenpham.oganicshop.util.DateTimeUtil;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

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
    private String deliveryMethod;
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
    @OrderBy("updateTime ASC")
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
    public OrderDtoResponse convertOrderToOrderDto() {
        OrderDtoResponse orderDto = new OrderDtoResponse();
        orderDto.setId(this.getId());
        orderDto.setShippingAddress(new ShippingAddressDto(this.contactReceiver, this.contactPhone, this.contactAddress));
        orderDto.setSubTotal(this.getSubTotal());
        orderDto.setShipFee(this.getShipFee());
        orderDto.setDiscount(this.getDiscount());
        orderDto.setTotal(this.getTotal());
        orderDto.setStatus(this.getStatus());
        orderDto.setMessage(this.getMessage());
        orderDto.setPaymentMethod(this.getPaymentMethod());
        orderDto.setNote(this.getNote());
        orderDto.setOrderDate(DateTimeUtil.dateTimeFormat(this.getOrderDate()));
        orderDto.setDeliveryDate(DateTimeUtil.dateTimeFormat((this.getDeliveryDate())));
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        StringBuilder summaryProductName = new StringBuilder("");
        this.getOrderDetails().forEach(orderDetail -> {
            summaryProductName.append(orderDetail.getProduct().getName());
            orderDetailDtoList.add(orderDetail.convertOrderDetailToOrderDetailDto());
        });
        orderDto.setOrderLogging(this.convertOrderLoggingToOrderLoggingDto());
        orderDto.setSummaryProductName(summaryProductName.toString());
        orderDto.setListOrderDetail(orderDetailDtoList);
        return orderDto;
    }

    // should order moder mapper
    public OrderDtoResponse convertToDtoNotDetail() {
        OrderDtoResponse orderDto = new OrderDtoResponse();
        orderDto.setId(this.getId());
        orderDto.setSubTotal(this.getSubTotal());
        orderDto.setShipFee(this.getShipFee());
        orderDto.setDiscount(this.getDiscount());
        orderDto.setTotal(this.getTotal());
        orderDto.setStatus(this.getStatus());
        orderDto.setOrderDate(DateTimeUtil.dateTimeFormat(this.getOrderDate()));
        orderDto.setDeliveryDate(DateTimeUtil.dateTimeFormat((this.getDeliveryDate())));
        StringBuilder summaryProductName = new StringBuilder("");
        this.getOrderDetails().forEach(orderDetail -> {
            summaryProductName.append(orderDetail.getProduct().getName());
        });
        orderDto.setSummaryProductName(summaryProductName.toString());
        return orderDto;
    }

    public OrderLoggingDto convertOrderLoggingToOrderLoggingDto() {
        OrderLoggingDto orderLoggingDto = new OrderLoggingDto();
        orderLoggingDto.setOrderId(this.getId());
        orderLoggingDto.setLatestStatus(this.getStatus());
        orderLoggingDto.setLastUpdatedTime(DateTimeUtil.dateTimeFormat(this.getDeliveryDate()));
        Set<OrderLoggingDto.LoggingOrderStatus> loggingStatus = new TreeSet<>();
        this.getOrderLoggings().forEach(ol -> loggingStatus.add(new OrderLoggingDto.LoggingOrderStatus(ol.getStatus(), DateTimeUtil.dateTimeFormat((ol.getUpdateTime())))));

        orderLoggingDto.setLoggingStatus(loggingStatus);
        return orderLoggingDto;
    }

}
