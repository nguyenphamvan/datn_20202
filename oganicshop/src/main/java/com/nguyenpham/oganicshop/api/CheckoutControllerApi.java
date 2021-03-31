package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.UserDto;
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

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfoPayment(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        OrderDtoResponse orderResponse = new OrderDtoResponse();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {
            orderResponse.setContactReceiver(user.getFullName());
            orderResponse.setContactAddress(user.getAddress());
            orderResponse.setContactPhone(user.getPhone());
            orderResponse.setSubTotal(cartService.totalSubCart(cart));
            orderResponse.setDiscount(0);
            orderResponse.setShipFee(Constant.SHIP_FEE_STANDARD); // mặc định ban đầu phí giao hàng là giao hàng tiêu chuẩn
            orderResponse.setTotal(orderResponse.getSubTotal() + orderResponse.getShipFee() - orderResponse.getDiscount());
            orderResponse.setDeliveryMethod("standard");
            orderResponse.setPaymentMethod("cod");
            return ResponseEntity.ok(orderResponse);
        }
        return new ResponseEntity<Object>("Có lỗi xảy ra khi kiểm tra thông tin thanh toán", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payOrder(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail, @RequestBody OrderDtoRequest orderDto) {
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
    public ResponseEntity<?> applyCoupon(HttpSession session, @RequestBody ObjectNode object, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        String couponCode = object.get("couponCode").asText();
        User user = myUserDetail.getUser();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {
            Discount discount = couponService.findCoupon(couponCode);
            if (discount != null) {
                int subTotal = cartService.totalSubCart(cart);
                int discountValue = orderService.applyCoupon(cart, discount);
                OrderDtoResponse orderResponse = new OrderDtoResponse();
                orderResponse.setContactReceiver(user.getFullName());
                orderResponse.setContactAddress(user.getAddress());
                orderResponse.setContactPhone(user.getPhone());
                orderResponse.setSubTotal(cartService.totalSubCart(cart));
                orderResponse.setSubTotal(subTotal);
                // ? nếu chọn hình thức giao hàng rồi mới áp dụng mã giảm giá thì ntn
                orderResponse.setShipFee(Constant.SHIP_FEE_STANDARD);
                orderResponse.setDiscount(discountValue);
                orderResponse.setTotal(orderResponse.getSubTotal() + orderResponse.getShipFee() - orderResponse.getDiscount());
                orderResponse.setDeliveryMethod("standard");
                orderResponse.setPaymentMethod("cod");

                return new ResponseEntity<Object>(orderResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("Mã giảm giá không hợp lệ", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Object>("Giỏ hàng trống, vui lòng thêm sản phẩm vào giỏ hàng", HttpStatus.BAD_REQUEST);
    }
}
