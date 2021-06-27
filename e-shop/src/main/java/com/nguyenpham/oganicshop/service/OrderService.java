package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.dto.OrderRequest;
import com.nguyenpham.oganicshop.dto.OrderResponse;
import com.nguyenpham.oganicshop.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface OrderService {

    long countNumberOrder();
    List<OrderResponse> getAll();
    Order save(Order order);
    void updateProductReviewed(long userId, long productId);
    int countTotalOrderPage(Long userId, int pageSize);
    OrderResponse getOrderDetail(long orderId);
    boolean updatedOrderStatus(long orderId, int statusId, String note);
    List<OrderResponse> getAllOrderByUserId(long userId);
    List<OrderItemDto> getListOrderItem(long orderId);
    List<OrderResponse> getOrdersHistory(long userId, int pageNum, int pageSize);
    Set<OrderItemDto> getListProductUnReviewed(long userId);
    int applyCoupon(HashMap<Long, CartItem> cart, Promotion promotion);
    Order paymentOrder(User user, HashMap<Long, CartItem> cart, OrderRequest orderDto);
    boolean cancelOrder(long orderId);
    OrderResponse getInfoCheckout(HashMap<Long, CartItem> cart);
    int countNumberOfProductInOrder(long productId);
}
