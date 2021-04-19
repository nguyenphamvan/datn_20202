package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/manager_products")
public class ManagerProductController {

    @GetMapping
    public String viewManagerProductsPage() {
        return "admin/manager-product";
    }

    @GetMapping("/product/{productId}")
    public String viewManagerProductDetail(@PathVariable("productId") long productId, Model model) {
        model.addAttribute("productId", productId);
        return "admin/manager-productDetail";
    }
}
