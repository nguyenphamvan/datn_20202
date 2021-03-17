package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // should user moder mapper
    public OrderDto convertUserToUserDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(this.getId());
        orderDto.setContactReceiver(this.getContactReceiver());
        orderDto.setContactPhone(this.getContactPhone());
        orderDto.setContactAddress(this.getContactAddress());
        orderDto.setSubTotal(this.getSubTotal());
        orderDto.setShipFee(this.getShipFee());
        orderDto.setDiscount(this.getDiscount());
        orderDto.setTotal(this.getTotal());
        if (this.getStatus() == 1) {
            orderDto.setStatus("Đang xử lý");
        } else if (this.getStatus() == 2) {
            orderDto.setStatus("Đã bàn giao vận chuyển");
        } else if (this.getStatus() == 3) {
            orderDto.setStatus("Đang vận chuyển");
        } else if (this.getStatus() == 4) {
            orderDto.setStatus("Giao hàng thành công");
        } else {
            orderDto.setStatus("Đã hủy");
        }
        orderDto.setPaymentMethod(this.getPaymentMethod());
        orderDto.setNote(this.getNote());
        orderDto.setOrderDate(this.getOrderDate());
        orderDto.setDeliveryDate(this.getDeliveryDate());
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        StringBuilder summaryProductname = new StringBuilder("");
        this.getOrderDetails().forEach(orderDetail -> {
            summaryProductname.append(orderDetail.getProduct().getProductName());
            orderDetailDtoList.add(orderDetail.convertOrderDetailToOrderDetailDto());
        });
        orderDto.setSummaryProductName(summaryProductname.toString());
        orderDto.setListOrderDetail(orderDetailDtoList);
        return orderDto;
    }

}
