package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.entity.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);
    int getTotalOrderPage(Long userId, int pageSize);
    List<OrderDto> getListOrderHistory(Long userId, int pageNum, int pageSize);
}
