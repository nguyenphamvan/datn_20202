package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.config.PaypalPaymentIntent;
import com.nguyenpham.oganicshop.config.PaypalPaymentMethod;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.dto.OrderDtoRequest;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.Promotion;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.*;
import com.nguyenpham.oganicshop.service.impl.PaypalService;
import com.nguyenpham.oganicshop.util.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutControllerApi {

    private Logger log = LoggerFactory.getLogger(CheckoutControllerApi.class);

    private PaypalService paypalService;
    private UserService userService;
    private CartService cartService;
    private OrderService orderService;
    private PromotionService promotionService;

    @Autowired
    public CheckoutControllerApi(PaypalService paypalService, CartService cartService, OrderService orderService,
                                 UserService userService, PromotionService promotionService) {
        this.paypalService = paypalService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
        this.promotionService = promotionService;
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfoPayment(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        Map<String, Object> response = new HashMap<>();
        response.put("order", orderService.getInfoCheckout(cart));
        if (cart != null) {
            response.put("order", orderService.getInfoCheckout(cart));
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<Object>("Có lỗi xảy ra khi kiểm tra thông tin thanh toán", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payOrder(HttpSession session, HttpServletRequest request,
                           @AuthenticationPrincipal MyUserDetail myUserDetail, @RequestBody OrderDtoRequest orderDto) {
        User user = myUserDetail.getUser();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {

            try {
                if (orderDto.getPaymentMethod().equals("cod")) {
                    orderService.paymentOrder(user, cart, orderDto);
                    session.removeAttribute(Constant.CART_SESSION_NAME);
                    return new ResponseEntity<Object>("/payment_success", HttpStatus.OK); // return home page
                } else if (orderDto.getPaymentMethod().equals("paypal")){
                    String cancelUrl = Utils.getBaseURL(request) + "/" + Constant.URL_PAYPAL_CANCEL;
                    String successUrl = Utils.getBaseURL(request) + "/" + Constant.URL_PAYPAL_SUCCESS;
                    String urlRedirect = null;
                    Payment payment = paypalService.createPayment(
                            (double) orderDto.getTotal(),
                            "USD",
                            PaypalPaymentMethod.paypal,
                            PaypalPaymentIntent.sale,
                            "payment description",
                            cancelUrl,
                            successUrl);

                    for (Links links : payment.getLinks()) {
                        if (links.getRel().equals("approval_url")) {
                            urlRedirect = links.getHref();
                        }
                    }
                    session.setAttribute("order", orderDto);
                    return new ResponseEntity<Object>(urlRedirect, HttpStatus.OK);
                }

            } catch (PayPalRESTException e) {
                log.error(e.getMessage());
                return new ResponseEntity<Object>("Hệ thống gặp lỗi, vui lòng liên hệ số điện thoại ...", HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Object>("Hệ thống gặp lỗi, vui lòng liên hệ số điện thoại ...", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/apply-couponCode")
    public ResponseEntity<?> applyCoupon(HttpSession session, @RequestBody ObjectNode object, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        String couponCode = object.get("couponCode").asText();
        User user = myUserDetail.getUser();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null) {
            Promotion promotion = promotionService.findCoupon(couponCode);
            if (promotion != null) {
                int discountValue = orderService.applyCoupon(cart, promotion);
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
        List<Promotion> myPromotions = new ArrayList<>();
        if (userDb.getOrders().size() == 0) {
            Promotion promotionFirstOrder = promotionService.findCoupon("FIRSTORDER50");
            myPromotions.add(promotionFirstOrder);
        }

        // select các dis count theo hạn còn áp dụng và số lượt sử dụng
        List<Promotion> loadPromotionList = promotionService.getAllCouponForOrder(cartService.totalSubCart(cart));
        loadPromotionList.remove(promotionService.findCoupon("FIRSTORDER50"));
        myPromotions.addAll(loadPromotionList);
        return ResponseEntity.ok(new HashSet<>(myPromotions));
    }
}
