package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CartService;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

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
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            cart = new HashMap<>();
        }
//        List<Category> listCategory = categoryService.getListCategory();
//        model.addAttribute("listCategory", listCategory);
        model.addAttribute("shipFee", Constant.SHIP_FEE_STANDARD);
        return "cart";
    }

}
