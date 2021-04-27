package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.AddressRequestDto;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter implements GeneralConverter<Order, OrderDtoRequest, OrderDtoResponse> {
    @Override
    public OrderDtoResponse entityToDto(Order order) {
        OrderDetailConverter odConverter = new OrderDetailConverter();
        OrderDtoResponse orderDto = new OrderDtoResponse();
        orderDto.setId(order.getId());
        orderDto.setShippingAddress(new AddressRequestDto(order.getContactReceiver(), order.getContactPhone(), order.getContactAddress()));
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
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        StringBuilder summaryProductName = new StringBuilder("");
        order.getOrderDetails().forEach(orderDetail -> {
            summaryProductName.append(orderDetail.getProduct().getName());
            orderDetailDtoList.add(odConverter.entityToDto(orderDetail));
        });
        orderDto.setOrderLogging(order.convertOrderLoggingToOrderLoggingDto());
        orderDto.setSummaryProductName(summaryProductName.toString());
        orderDto.setListOrderDetail(orderDetailDtoList);
        return orderDto;
    }

    @Override
    public Order dtoToEntity(OrderDtoRequest d) {
        return null;
    }
}
