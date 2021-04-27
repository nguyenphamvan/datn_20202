package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.converter.OrderConverter;
import com.nguyenpham.oganicshop.converter.OrderDetailConverter;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.*;
import com.nguyenpham.oganicshop.repository.OrderDetailRepository;
import com.nguyenpham.oganicshop.repository.OrderRepository;
import com.nguyenpham.oganicshop.repository.ShippingAddressRepository;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ShippingAddressRepository shippingAddressRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.shippingAddressRepository = shippingAddressRepository;
    }

    @Override
    public long countNumberOrder() {
        return orderRepository.count();
    }

    @Override
    public List<OrderDtoResponse> getAll() {
        OrderConverter orderConverter = new OrderConverter();
        return orderRepository.findAll().stream()
                .map(o -> {
                    OrderDtoResponse response = orderConverter.entityToDto(o);
                    response.setListOrderDetail(null);
                    return response;
                }).collect(Collectors.toList());
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
    public List<OrderDtoResponse> getAllOrderByUserId(long userId) {
        OrderConverter converter = new OrderConverter();
        return orderRepository.findAllByUserId(userId).stream()
                .map(od -> {
                    OrderDtoResponse response = converter.entityToDto(od);
                    response.setListOrderDetail(null);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public boolean updatedOrderStatus(long orderId, int statusId, String message) {
        Order order = orderRepository.findById(orderId).get();
        OrderLogging orderLogging = new OrderLogging(statusId);
        orderLogging.setOrder(order);
        order.setStatus(statusId);
        order.setMessage(DateTimeUtil.dateTimeFormat(new Date()) + " - " + message);
        order.addLogOrder(orderLogging);

        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrderDetailDto> getListOrderItem(long orderId) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        List<OrderDetailDto> orderDetailDtos = orderRepository.findById(orderId).get().getOrderDetails().stream()
                .map(od -> odConverter.entityToDto(od))
                .collect(Collectors.toList());
        return orderDetailDtos;
    }

    @Override
    public List<OrderDtoResponse> getListOrderHistory(long userId, int pageNum, int pageSize) {
        OrderConverter converter = new OrderConverter();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").ascending());
        Page<Order> page = orderRepository.findOrdersByUserId(userId, pageable);
        List<Order> ordersHistory = page.getContent();
        List<OrderDtoResponse> ordersDtoHistory = new ArrayList<>();
        ordersHistory.forEach(order -> {
            ordersDtoHistory.add(converter.entityToDto(order));
        });
        return ordersDtoHistory;
    }

    @Override
    public Set<OrderDetailDto> getListProductNotReviewed(long userId) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        return orderDetailRepository.findAllByReviewedIsFalse(userId).stream()
                .map(od -> odConverter.entityToDto(od))
                .collect(Collectors.toSet());
    }

    @Override
    public int applyCoupon(HashMap<Long, CartItem> cart, Promotion promotion) {
        int total = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            total += item.getValue().calculateTotalItem();
        }
        int discountValue = 0;
        if (promotion.getDiscountPercent() > 0 && promotion.getDiscountPrice() == 0) {
            discountValue = (int) (total * promotion.getDiscountPercent());
        } else if (promotion.getDiscountPercent() == 0 && promotion.getDiscountPrice() > 0) {
            discountValue = promotion.getDiscountPrice();
        }

        if (total > promotion.getMinOrderValue()) {
            discountValue = discountValue > promotion.getMaxDiscountValue() ?  promotion.getMaxDiscountValue() : discountValue;
        }
        return discountValue;
    }

    @Override
    public void paymentOrder(User user, HashMap<Long, CartItem> cart, OrderDtoRequest orderDto) {
        Order order = new Order();
        order.setUser(user);
        order.setContactReceiver(orderDto.getShippingAddress().getContactReceiver());
        order.setContactAddress(orderDto.getShippingAddress().getContactAddress());
        order.setContactPhone(orderDto.getShippingAddress().getContactPhone());
        order.setNote(orderDto.getNote());
        order.setStatus(0);
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setDeliveryMethod(orderDto.getDeliveryMethod());
        order.setMessage(DateTimeUtil.dateTimeFormat(new Timestamp(System.currentTimeMillis())) + " - " + Constant.MAP_ORDER_TRACKING_STATUS.get(1));
        order.setSubTotal(orderDto.getSubTotal());
        order.setShipFee(orderDto.getShipFee());
        order.setDiscount(orderDto.getDiscount());
        order.setTotal(order.getSubTotal() + order.getShipFee() - order.getDiscount());
        for (CartItem item : cart.values()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(item.calculateTotalItem());
            orderDetail.setDiscount(item.getDiscount());
            orderDetail.setTotalPrice(item.calculateTotalItem() - item.getDiscount());
            orderDetail.setProduct(item.getProduct());
            orderDetail.setOrder(order);
            order.addOrderDetail(orderDetail);
        }
        this.save(order);
    }

    @Override
    public boolean cancelOrder(long userId, long orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order.getUser().getId() == userId && order.getStatus() == 0) {
            order.setStatus(4);
            try {
                orderRepository.save(order);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public OrderDtoResponse getInfoCheckout(HashMap<Long, CartItem> cart) {
        Address addressDefault = shippingAddressRepository.findByAddrDefaultIsTrue();
        int subCart = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            subCart += item.getValue().calculateTotalItem();
        }
        OrderDtoResponse orderResponse = new OrderDtoResponse();
        orderResponse.setShippingAddress(new AddressRequestDto(addressDefault.getContactReceiver(), addressDefault.getContactPhone(), addressDefault.getContactAddress(), addressDefault.isAddrDefault()));
        orderResponse.setSubTotal(subCart);
        orderResponse.setDiscount(0);
        orderResponse.setShipFee(Constant.SHIP_FEE_STANDARD); // mặc định ban đầu phí giao hàng là giao hàng tiêu chuẩn
        orderResponse.setTotal(orderResponse.getSubTotal() + orderResponse.getShipFee() - orderResponse.getDiscount());
        orderResponse.setDeliveryMethod("standard");
        orderResponse.setPaymentMethod("cod");

        return orderResponse;
    }

    @Override
    public int countNumberOfProductInOrder(long productId) {
        return orderDetailRepository.countByProductId(productId);
    }


}
