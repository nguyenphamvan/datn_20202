package com.nguyenpham.oganicshop.controller.user;

import com.nguyenpham.oganicshop.entity.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String getCheckoutPage(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute("myCart");
        if (cart == null) {
            session.setAttribute("cartEmpty", "Không có sản phẩm nào trong giỏ hàng");
            return "redirect:/cart.html";
        }
        return "user/checkout";
    }
}
