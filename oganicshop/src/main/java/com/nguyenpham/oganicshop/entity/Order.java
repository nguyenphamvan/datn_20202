package com.nguyenpham.oganicshop.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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
    private String paymentMethod;
    private String note;
    @CreationTimestamp
    private Date orderDate;
    @UpdateTimestamp
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;

    public void addOrderDetail(OrderDetail orderDetail) {
        if(orderDetails == null) {
            this.orderDetails = new HashSet<>();
        }
        this.orderDetails.add(orderDetail);
    }

}
