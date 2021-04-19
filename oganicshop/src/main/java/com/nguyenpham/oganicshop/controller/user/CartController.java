package com.nguyenpham.oganicshop.controller.user;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/cart.html")
public class CartController {

    private CategoryService categoryService;

    @Autowired
    public CartController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
//        model.addAttribute("shipFee", Constant.SHIP_FEE_STANDARD);
        return "user/cart";
    }

}
