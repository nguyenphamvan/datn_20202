package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface OrderService {

    long countNumberOrder();
    List<OrderDtoResponse> getAll();
    Order save(Order order);
    void updateProductReviewed(long userId, long productId);
    int getTotalOrderPage(Long userId, int pageSize);
    OrderDtoResponse getOrderById(long orderId);
    boolean updatedOrderStatus(long orderId, int statusId, String note);
    List<OrderDtoResponse> getAllOrderByUserId(long userId);
    List<OrderItemDto> getListOrderItem(long orderId);
    List<OrderDtoResponse> getOrdersHistory(long userId, int pageNum, int pageSize);
    Set<OrderItemDto> getListProductUnReviewed(long userId);
    int applyCoupon(HashMap<Long, CartItem> cart, Promotion promotion);
    Order paymentOrder(User user, HashMap<Long, CartItem> cart, OrderDtoRequest orderDto);
    boolean cancelOrder(long userId, long orderId);
    OrderDtoResponse getInfoCheckout(HashMap<Long, CartItem> cart);
    int countNumberOfProductInOrder(long productId);
}
