package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.entity.OrderItem;

public class OrderDetailConverter implements GeneralConverter<OrderItem, OrderItemDto, OrderItemDto>{
    @Override
    public OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductUrl(orderItem.getProduct().getProductUrl());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getTitle());
        orderItemDto.setImage(orderItem.getProduct().getMainImage());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setDiscount(orderItem.getDiscount());
        orderItemDto.setRawTotal(orderItem.getTotalPrice());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setReviewed(orderItem.isReviewed());
        return orderItemDto;
    }

    @Override
    public OrderItem dtoToEntity(OrderItemDto d) {
        return null;
    }
}
