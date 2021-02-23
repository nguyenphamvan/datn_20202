package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.service.CartService;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutControllerApi {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/payment")
    public ResponseEntity<?> payOrder(HttpSession session, @RequestBody Order order) {

        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {
            order.setStatus(1);
            order.setSubTotal(cartService.totalSubCart(cart));
            order.setTotal(cartService.totalSubCart(cart) + order.getShipFee());
            for (CartItem item : cart.values()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setPrice(item.caculateTotalItem());
                orderDetail.setDiscount(item.getDiscount());
                orderDetail.setTotalPrice(item.caculateTotalItem() - item.getDiscount());
                orderDetail.setProduct(item.getProduct());
                orderDetail.setOrder(order);

                order.addOrderDetail(orderDetail);
            }

            Order orderSaved = orderService.save(order);
            session.removeAttribute(Constant.CART_SESSION_NAME);
            return new ResponseEntity<Object>(orderSaved, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("chưa có mặt hàng nào được chọn", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
