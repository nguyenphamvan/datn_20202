package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface OrderService {

    Order save(Order order);
    OrderDetail editReviewed(OrderDetailDto orderDetailDto);
    int getTotalOrderPage(Long userId, int pageSize);
    OrderDtoResponse getOrderById(long orderId);
    List<OrderDetailDto> getListOrderItem(long orderId);
    List<OrderDtoResponse> getListOrderHistory(Long userId, int pageNum, int pageSize);
    Set<OrderDetailDto> getListProductNotReviewed(long userId);
    int applyCoupon(HashMap<Long, CartItem> cart, Discount discount);
    void paymentOrder(User user, HashMap<Long, CartItem> cart, OrderDtoRequest orderDto);
    public OrderDtoResponse getInfoCheckout(HashMap<Long, CartItem> cart);
}
