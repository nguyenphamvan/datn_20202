package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AccountController {

    private CategoryService categoryService;

    @Autowired
    public AccountController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"/customer/account/edit", "/customer/address", "/nhan-xet-san-pham-ban-da-mua",
            "/customer/notification", "/customer/wishlist", "/customer/review"} )
    public String myAccountPage(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
        return "account";
    }
}
