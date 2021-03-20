package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CartService;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class CheckoutController {

    private OrderService orderService;

    private CartService cartService;

    @Autowired
    public CheckoutController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/checkout.html")
    public String getCheckoutPage(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail, Model model) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute("myCart");
        if (cart == null) {
            session.setAttribute("cartEmpty", "Không có sản phẩm nào trong giỏ hàng");
            return "redirect:/cart.html";
        }
        model.addAttribute("user", myUserDetail.getUser());
        model.addAttribute("order", new Order());
        model.addAttribute("shipFee", Constant.SHIP_FEE);
        return "checkout";
    }

    @PostMapping("/payment")
    public String payOrder(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail, @ModelAttribute Order order) {
        User user = myUserDetail.getUser();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute("myCart");
        if (cart != null) {
            order.setUser(user);
            order.setStatus("Đặt hàng thành công");
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
            try {
                Order orderSaved = orderService.save(order);
                session.removeAttribute(Constant.CART_SESSION_NAME);
                session.removeAttribute("subCart");
                // return về trang chủ hoặc trang thông báo thanh toán thành công
                return "redirect:/";
            } catch (Exception e) {
                return "/403";
            }
        } else {
            return "redirect:/cart.html";
        }

    }
}
