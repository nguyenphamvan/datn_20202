package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_logging")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @UpdateTimestamp
    private Timestamp updateTime;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;
}
