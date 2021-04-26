package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/manager_orders")
public class ManagerOrderController {

    @GetMapping
    public String viewManagerOrdersPage() {
        return "admin/manager-order";
    }

    @GetMapping("/{orderId}")
    public String viewManagerOrderDetail(@PathVariable("orderId") long orderId,
                                         @RequestParam(value = "userOrderHistory", required = false, defaultValue = "false") boolean userOrderHistory,
                                         Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("userOrderHistory", userOrderHistory);
        return "admin/manager-orderDetail";
    }
}
