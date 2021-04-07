package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CartService;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class CheckoutController {

    private OrderService orderService;
    private CategoryService categoryService;
    private CartService cartService;

    @Autowired
    public CheckoutController(OrderService orderService, CartService cartService, CategoryService categoryService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @GetMapping("/checkout.html")
    public String getCheckoutPage(HttpSession session, @AuthenticationPrincipal MyUserDetail myUserDetail, Model model) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute("myCart");
        if (cart == null) {
            session.setAttribute("cartEmpty", "Không có sản phẩm nào trong giỏ hàng");
            return "redirect:/cart.html";
        }
        model.addAttribute("listCategory", categoryService.getListCategory());
        return "checkout";
    }
}
