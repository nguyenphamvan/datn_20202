package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sales/")
public class SalesController {

    private CategoryService categoryService;

    @Autowired
    public SalesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/order/history")
    public String viewOrderHistory(Model model) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
        return "order-manager";
    }

    @GetMapping("/order/view/{orderId}")
    public String viewOrderDetail(Model model, @PathVariable("orderId") long orderId) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

    @GetMapping("/order/tracking/{orderId}")
    public String viewOrderTracking(Model model, @PathVariable("orderId") long orderId) {
        List<Category> listCategory = categoryService.getListCategory();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("orderId", orderId);
        return "order-tracking";
    }
}
