package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderLogging;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Order save(Order order);
    int getTotalOrderPage(Long userId, int pageSize);
    Order getOrderById(long orderId);
    List<OrderDto> getListOrderHistory(Long userId, int pageNum, int pageSize);
}
