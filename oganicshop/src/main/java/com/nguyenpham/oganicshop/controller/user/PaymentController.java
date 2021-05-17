package com.nguyenpham.oganicshop.controller.user;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.OrderRequest;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.impl.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class PaymentController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping(Constant.URL_PAYPAL_CANCEL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(Constant.URL_PAYPAL_SUCCESS)
    public String successPay(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail,
                             @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            User user = myUserDetail.getUser();
            HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
            Payment payment = paypalService.executePayment(paymentId, payerId);
            OrderRequest order = (OrderRequest) session.getAttribute("order");

            if (payment.getState().equals("approved")) {
                orderService.paymentOrder(user, cart, order);
                session.removeAttribute("order");
                session.removeAttribute(Constant.CART_SESSION_NAME);
                return "redirect:/payment_success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/403";
    }

}
