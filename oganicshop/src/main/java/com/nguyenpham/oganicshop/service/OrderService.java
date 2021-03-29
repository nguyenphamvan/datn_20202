package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.dto.ProductDto;
import com.nguyenpham.oganicshop.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface OrderService {

    Order save(Order order);
    OrderDetail editReviewed(OrderDetailDto orderDetailDto);
    int getTotalOrderPage(Long userId, int pageSize);
    OrderDto getOrderById(long orderId);
    List<OrderDetailDto> getListOrderItem(long orderId);
    List<OrderDto> getListOrderHistory(Long userId, int pageNum, int pageSize);
    Set<OrderDetailDto> getListProductNotReviewed(long userId);
    int applyCoupon(HashMap<Long, CartItem> cart, Discount discount);
    void paymentOrder(User user, HashMap<Long, CartItem> cart, OrderDto orderDto);
}
