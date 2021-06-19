package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order_status")
@Data
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int status;
    @UpdateTimestamp
    private Timestamp updateTime;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    @JsonIgnore
    private Order order;

    public OrderStatus() {
    }

    public OrderStatus(int status) {
        this.status = status;
    }
}
