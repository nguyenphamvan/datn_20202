package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.OrderStatusDto;
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
@EqualsAndHashCode(exclude = {"orderDetails", "user", "orderStatuses"})
@ToString(exclude = {"orderDetails", "user", "orderStatuses"})
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
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("updateTime ASC")
    private Set<OrderStatus> orderStatuses;

    public void addOrderDetail(OrderItem orderItem) {
        if(this.orderItems == null) {
            this.orderItems = new HashSet<>();
        }
        this.orderItems.add(orderItem);
    }

    public void addLogOrder(OrderStatus logOrder) {
        if(this.orderStatuses == null) {
            this.orderStatuses = new HashSet<>();
        }
        this.orderStatuses.add(logOrder);
    }

    public OrderStatusDto convertOrderLoggingToOrderLoggingDto() {
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setOrderId(this.getId());
        orderStatusDto.setLatestStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(this.getStatus()));
        orderStatusDto.setLastUpdatedTime(DateTimeUtil.dateTimeFormat(this.getDeliveryDate()));
        Set<OrderStatusDto.LoggingOrderStatus> loggingStatus = new TreeSet<>();
        this.getOrderStatuses().forEach(ol -> loggingStatus.add(new OrderStatusDto.LoggingOrderStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(ol.getStatus()), DateTimeUtil.dateTimeFormat((ol.getUpdateTime())))));

        orderStatusDto.setLoggingStatus(loggingStatus);
        return orderStatusDto;
    }

}
