package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.dto.ProductDto;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.entity.OrderLogging;
import com.nguyenpham.oganicshop.repository.OrderDetailRepository;
import com.nguyenpham.oganicshop.repository.OrderLoggingRepository;
import com.nguyenpham.oganicshop.repository.OrderRepository;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Order save(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return savedOrder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderDetail editReviewed(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetailDb = orderDetailRepository.findById(orderDetailDto.getId()).get();
        orderDetailDb.setReviewed(orderDetailDto.isReviewed());
        return orderDetailRepository.save(orderDetailDb);
    }

    @Override
    public int getTotalOrderPage(Long userId, int pageSize) {
        List<Order> allOrder = orderRepository.findAllByUserId(userId);
        if (allOrder.size() <= pageSize) {
            return 1;
        } else {
            return (allOrder.size() / pageSize + 1);
        }

    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public List<OrderDto> getListOrderHistory(Long userId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").ascending());
        Page<Order> page = orderRepository.findOrdersByUserId(userId, pageable);
        List<Order> ordersHistory = page.getContent();
        List<OrderDto> ordersDtoHistory = new ArrayList<>();
        ordersHistory.forEach(order -> {
            ordersDtoHistory.add(order.convertOrderToOrderDto());
        });
        return ordersDtoHistory;
    }

    @Override
    public Set<OrderDetailDto> getListProductNotReviewed(long userId) {
        return orderDetailRepository.findAllByReviewedIsFalse(userId).stream()
                .map(od -> od.convertOrderDetailToOrderDetailDto())
                .collect(Collectors.toSet());
    }
}
