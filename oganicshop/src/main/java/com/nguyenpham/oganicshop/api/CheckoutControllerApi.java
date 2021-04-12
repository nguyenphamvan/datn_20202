package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.Discount;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutControllerApi {

    @Autowired
    private UserService userService;
    private CartService cartService;
    private OrderService orderService;
    private CouponService couponService;
    private CategoryService categoryService;

    @Autowired
    public CheckoutControllerApi(CartService cartService, OrderService orderService, CategoryService categoryService, CouponService couponService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.couponService = couponService;
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfoPayment(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("order", orderService.getInfoCheckout(cart));
        if (cart != null) {
            response.put("order", orderService.getInfoCheckout(cart));
            return ResponseEntity.ok(response);
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
                int discountValue = orderService.applyCoupon(cart, discount);
                // ? nếu chọn hình thức giao hàng rồi mới áp dụng mã giảm giá thì ntn
                OrderDtoResponse orderResponse = orderService.getInfoCheckout(cart);
                orderResponse.setDiscount(discountValue);
                orderResponse.setTotal(orderResponse.getSubTotal() + orderResponse.getShipFee() - orderResponse.getDiscount());
                return new ResponseEntity<Object>(orderResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("Mã giảm giá không hợp lệ", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Object>("Giỏ hàng trống, vui lòng thêm sản phẩm vào giỏ hàng", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getMyDiscount")
    public ResponseEntity<?> getMyDiscount(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        User userDb = userService.findUserByEmail(user.getEmail());
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        List<Discount> myDiscounts = new ArrayList<>();
        if (userDb.getOrders().size() == 0) {
            Discount discountFirstOrder = couponService.findCoupon("FIRSTORDER50");
            myDiscounts.add(discountFirstOrder);
        }

        // select các dis count theo hạn còn áp dụng và số lượt sử dụng
        List<Discount> loadDiscountList = couponService.getAllDiscountForOrder(cartService.totalSubCart(cart));
        loadDiscountList.remove(couponService.findCoupon("FIRSTORDER50"));
        myDiscounts.addAll(loadDiscountList);
        return ResponseEntity.ok(new HashSet<>(myDiscounts));
    }
}
