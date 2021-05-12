package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.AddressRequestDto;
import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderItem;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.util.DateTimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderConverter implements GeneralConverter<Order, OrderDtoRequest, OrderDtoResponse> {
    @Override
    public OrderDtoResponse entityToDto(Order order) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        OrderDtoResponse orderDto = new OrderDtoResponse();
        orderDto.setId(order.getId());
        orderDto.setAddress(new AddressRequestDto(order.getContactReceiver(), order.getContactPhone(), order.getContactAddress()));
        orderDto.setSubTotal(order.getSubTotal());
        orderDto.setShipFee(order.getShipFee());
        orderDto.setDiscount(order.getDiscount());
        orderDto.setTotal(order.getTotal());
        orderDto.setStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(order.getStatus()));
        orderDto.setMessage(order.getMessage());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setNote(order.getNote());
        orderDto.setOrderDate(DateTimeUtil.dateTimeFormat(order.getOrderDate()));
        orderDto.setDeliveryDate(DateTimeUtil.dateTimeFormat((order.getDeliveryDate())));
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        StringBuilder summaryProductName = new StringBuilder("");
        order.getOrderItems().forEach(item -> {
            summaryProductName.append(item.getProduct().getName());
            orderItemDtoList.add(odConverter.entityToDto(item));
        });
        orderDto.setOrderLogging(order.convertOrderLoggingToOrderLoggingDto());
        orderDto.setSummaryProductName(summaryProductName.toString());
        orderDto.setListOrderDetail(orderItemDtoList);
        return orderDto;
    }

    @Override
    public Order dtoToEntity(OrderDtoRequest d) {
        return null;
    }

    public Order dtoToEntity(User user, HashMap<Long, CartItem> cart, OrderDtoRequest orderDto) {
        Order order = new Order();
        order.setUser(user);
        order.setContactReceiver(orderDto.getAddress().getContactReceiver());
        order.setContactAddress(orderDto.getAddress().getContactAddress());
        order.setContactPhone(orderDto.getAddress().getContactPhone());
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
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.calculateTotalItem());
            orderItem.setDiscount(item.getDiscount());
            orderItem.setTotalPrice(item.calculateTotalItem() - item.getDiscount());
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            order.addOrderDetail(orderItem);
        }
        return order;
    }
}
