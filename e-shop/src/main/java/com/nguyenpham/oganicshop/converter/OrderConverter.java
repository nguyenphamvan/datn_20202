package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.AddressRequest;
import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.dto.OrderRequest;
import com.nguyenpham.oganicshop.dto.OrderResponse;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderItem;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.util.DateTimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderConverter implements GeneralConverter<Order, OrderRequest, OrderResponse> {
    @Override
    public OrderResponse entityToDto(Order order) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        OrderResponse orderDto = new OrderResponse();
        orderDto.setId(order.getId());
        orderDto.setAddress(new AddressRequest(order.getContactReceiver(), order.getContactPhone(), order.getContactAddress()));
        orderDto.setSubTotal(order.getSubTotal());
        orderDto.setShipFee(order.getShipFee());
        orderDto.setDiscount(order.getDiscount());
        orderDto.setTotal(order.getTotal());
        orderDto.setStatus(Constant.MAP_ORDER_TRACKING_STATUS.get(order.getStatus()));
        orderDto.setMessage(order.getMessage());
        orderDto.setNote(order.getNote());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setDeliveryDate(order.getDeliveryDate());
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        order.getOrderItems().forEach(item -> {
            orderItemDtoList.add(odConverter.entityToDto(item));
        });

        orderDto.setOrderStatus(order.convertOrderLoggingToOrderLoggingDto());
        String summaryListItem = "";
        if (order.getOrderItems().size() > 1) {
            summaryListItem = new ArrayList<>(order.getOrderItems()).get(0).getProduct().getTitle()
                    + " và " + (order.getOrderItems().size() - 1) + " sản phẩm khác";
        } else {
            summaryListItem = new ArrayList<>(order.getOrderItems()).get(0).getProduct().getTitle();
        }
        orderDto.setSummaryProductName(summaryListItem);
        orderDto.setListOrderDetail(orderItemDtoList);
        return orderDto;
    }

    @Override
    public Order dtoToEntity(OrderRequest d) {
        return null;
    }

    public Order dtoToEntity(User user, HashMap<Long, CartItem> cart, OrderRequest request) {
        Order order = new Order();
        order.setUser(user);
        order.setContactReceiver(request.getAddress().getContactReceiver());
        order.setContactAddress(request.getAddress().getContactAddress());
        order.setContactPhone(request.getAddress().getContactPhone());
        order.setNote(request.getNote());
        order.setStatus(0);
        order.setMessage(DateTimeUtil.dateTimeFormat(new Timestamp(System.currentTimeMillis())) + " - " + Constant.MAP_ORDER_TRACKING_STATUS.get(1));
        order.setSubTotal(request.getSubTotal());
        order.setShipFee(request.getShipFee());
        order.setDiscount(request.getDiscount());
        order.setTotal(order.getSubTotal() + order.getShipFee() - order.getDiscount());
        for (CartItem item : cart.values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.calculateTotalItem());
            orderItem.setDiscount(item.getDiscount());
            orderItem.setTotalPrice(item.calculateTotalItem() - item.getDiscount()*item.getProduct().getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            order.addOrderDetail(orderItem);
        }
        return order;
    }
}
