package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.entity.OrderDetail;

public class OrderDetailConverter implements GeneralConverter<OrderDetail, OrderDetailDto, OrderDetailDto>{
    @Override
    public OrderDetailDto entityToDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setId(orderDetail.getId());
        orderDetailDto.setProductId(orderDetail.getProduct().getId());
        orderDetailDto.setProductName(orderDetail.getProduct().getName());
        orderDetailDto.setProductUrl(orderDetail.getProduct().getUrl());
        orderDetailDto.setImage( "/images/products/" + orderDetail.getProduct().getId() + "/" + orderDetail.getProduct().getImage().split(",")[0]);
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setDiscount(orderDetail.getDiscount());
        orderDetailDto.setRawTotal(orderDetail.getTotalPrice());
        orderDetailDto.setPrice(orderDetail.getPrice());
        orderDetailDto.setSupplierName(orderDetail.getProduct().getSupplier().getName());
        orderDetailDto.setReviewed(orderDetail.isReviewed());
        return orderDetailDto;
    }

    @Override
    public OrderDetail dtoToEntity(OrderDetailDto d) {
        return null;
    }
}
