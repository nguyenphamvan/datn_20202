package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.OrderItemDto;
import com.nguyenpham.oganicshop.entity.OrderItem;

public class OrderDetailConverter implements GeneralConverter<OrderItem, OrderItemDto, OrderItemDto>{
    @Override
    public OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setProductUrl(orderItem.getProduct().getUrl());
        orderItemDto.setImage( "/images/products/" + orderItem.getProduct().getId() + "/" + orderItem.getProduct().getImage().split(",")[0]);
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setDiscount(orderItem.getDiscount());
        orderItemDto.setRawTotal(orderItem.getTotalPrice());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setSupplierName(orderItem.getProduct().getSupplier().getName());
        orderItemDto.setReviewed(orderItem.isReviewed());
        return orderItemDto;
    }

    @Override
    public OrderItem dtoToEntity(OrderItemDto d) {
        return null;
    }
}
