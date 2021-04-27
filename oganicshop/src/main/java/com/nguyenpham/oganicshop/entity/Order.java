package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import com.nguyenpham.oganicshop.dto.AddressRequestDto;
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
    private int status;
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

    public OrderLoggingDto convertOrderLoggingToOrderLoggingDto() {
        OrderLoggingDto orderLoggingDto = new OrderLoggingDto();
        orderLoggingDto.setOrderId(this.getId());
        orderLoggingDto.setLatestStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(this.getStatus()));
        orderLoggingDto.setLastUpdatedTime(DateTimeUtil.dateTimeFormat(this.getDeliveryDate()));
        Set<OrderLoggingDto.LoggingOrderStatus> loggingStatus = new TreeSet<>();
        this.getOrderLoggings().forEach(ol -> loggingStatus.add(new OrderLoggingDto.LoggingOrderStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(ol.getStatus()), DateTimeUtil.dateTimeFormat((ol.getUpdateTime())))));

        orderLoggingDto.setLoggingStatus(loggingStatus);
        return orderLoggingDto;
    }

}
