package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/cart.html")
public class CartController {

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            cart = new HashMap<>();
        }
        model.addAttribute("shipFee", Constant.SHIP_FEE);
        return "cart";
    }

}
