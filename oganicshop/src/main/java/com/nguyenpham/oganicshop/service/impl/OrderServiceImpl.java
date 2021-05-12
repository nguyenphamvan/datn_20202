package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.converter.OrderConverter;
import com.nguyenpham.oganicshop.converter.OrderDetailConverter;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.*;
import com.nguyenpham.oganicshop.repository.OrderDetailRepository;
import com.nguyenpham.oganicshop.repository.OrderRepository;
import com.nguyenpham.oganicshop.repository.ShippingAddressRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateProductReviewed(long userId, long productId) {
        try {
            Set<OrderItem> setOrderItemDb = orderDetailRepository.findAllByProductUnReviewedOfUser(userId,productId);
            setOrderItemDb.forEach(orderDetail -> {
                orderDetail.setReviewed(true);
            });
            orderDetailRepository.saveAll(setOrderItemDb);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public OrderDtoResponse getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return new OrderConverter().entityToDto(order);
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
    public List<OrderItemDto> getListOrderItem(long orderId) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        List<OrderItemDto> orderItemDtos = orderRepository.findById(orderId).get().getOrderItems().stream()
                .map(od -> odConverter.entityToDto(od))
                .collect(Collectors.toList());
        return orderItemDtos;
    }

    @Override
    public List<OrderDtoResponse> getOrdersHistory(long userId, int pageNum, int pageSize) {
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
    public Set<OrderItemDto> getListProductUnReviewed(long userId) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        Set<Long> productsNotCommentInOrders = new HashSet<>();
        Set<OrderItemDto> listOrderDetail = orderDetailRepository.findAllByReviewedIsFalse(userId).stream()
                .filter(od -> productsNotCommentInOrders.add(od.getProduct().getId()))
                .map(od -> odConverter.entityToDto(od))
                .collect(Collectors.toSet());
        return listOrderDetail;
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
            discountValue = discountValue > promotion.getMaxDiscountValue() ? promotion.getMaxDiscountValue() : discountValue;
        }
        return discountValue;
    }

    @Override
    @Transactional
    public Order paymentOrder(User user, HashMap<Long, CartItem> cart, OrderDtoRequest orderDto) {
        Order order = new OrderConverter().dtoToEntity(user, cart, orderDto);
        return this.save(order);
    }

    @Override
    public boolean cancelOrder(long userId, long orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order.getUser().getId() == userId && order.getStatus() == 0) {
            order.setStatus(3);
            OrderLogging orderLogging = new OrderLogging(3);
            orderLogging.setOrder(order);
            order.setMessage(DateTimeUtil.dateTimeFormat(new Date()) + " - Bạn đã hủy đơn hàng");
            order.addLogOrder(orderLogging);
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
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Address addressDefault = shippingAddressRepository.findByAddrDefaultIsTrue(user.getId());
        int subCart = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            subCart += item.getValue().calculateTotalItem();
        }
        OrderDtoResponse orderResponse = new OrderDtoResponse();
        if (addressDefault != null) {
            orderResponse.setAddress(new AddressRequestDto(addressDefault.getContactReceiver(), addressDefault.getContactPhone(),
                    addressDefault.getContactAddress(), addressDefault.isAddrDefault()));
        } else {
            orderResponse.setAddress(null);
        }
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
