package com.nguyenpham.oganicshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude= {"order", "product"})
@ToString(exclude= {"order", "product"})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private int price;
    private int discount;
    private int totalPrice;
    @Column(columnDefinition = "boolean default false")
    private boolean reviewed;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;

    public OrderDetailDto convertOrderDetailToOrderDetailDto() {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setId(this.getId());
        orderDetailDto.setProductName(this.getProduct().getProductName());
        orderDetailDto.setQuantity(this.getQuantity());
        orderDetailDto.setDiscount(this.getDiscount());
        orderDetailDto.setTotalPrice(this.getTotalPrice());
        orderDetailDto.setPrice(this.getPrice());
        orderDetailDto.setReviewed(this.isReviewed());
        orderDetailDto.setSupplierName(this.getProduct().getSupplier().getName());
        return orderDetailDto;
    }

}
