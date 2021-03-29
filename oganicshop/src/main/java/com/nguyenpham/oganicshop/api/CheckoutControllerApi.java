package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDto;
import com.nguyenpham.oganicshop.entity.Discount;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CartService;
import com.nguyenpham.oganicshop.service.CouponService;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutControllerApi {

    private OrderService orderService;
    private CartService cartService;
    private CouponService couponService;

    @Autowired
    public CheckoutControllerApi(OrderService orderService, CartService cartService, CouponService couponService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.couponService = couponService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payOrder(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail, @RequestBody OrderDto orderDto) {
        User user = myUserDetail.getUser();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) { // should convert order to orderDto use ordermapper
            session.removeAttribute(Constant.CART_SESSION_NAME);
            try {
                orderService.paymentOrder(user, cart, orderDto);
                return new ResponseEntity<Object>("Thanh toán thành công", HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>("Hệ thống gặp lỗi, vui lòng liên hệ số điện thoại ...", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<Object>("Chưa có mặt hàng nào được chọn", HttpStatus.OK);
        }
    }

    @PostMapping("/apply-couponCode")
    public ResponseEntity<?> applyCoupon(HttpSession session, @RequestBody ObjectNode object) {
        String couponCode = object.get("couponCode").asText();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {
            Discount discount = couponService.findCoupon(couponCode);
            if (discount != null) {
                int subTotal = cartService.totalSubCart(cart);
                int discountValue = orderService.applyCoupon(cart, discount);
                OrderDto orderDto = new OrderDto();
                orderDto.setSubTotal(subTotal);
                orderDto.setShipFee(Constant.SHIP_FEE);
                orderDto.setDiscount(discountValue);
                orderDto.setTotal(subTotal + Constant.SHIP_FEE + discountValue);

                return new ResponseEntity<Object>(orderDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("Mã giảm giá không hợp lệ", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Object>("Giỏ hàng trống, vui lòng thêm sản phẩm vào giỏ hàng", HttpStatus.BAD_REQUEST);
    }
}
