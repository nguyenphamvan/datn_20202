package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.repository.OrderRepository;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return savedOrder;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
