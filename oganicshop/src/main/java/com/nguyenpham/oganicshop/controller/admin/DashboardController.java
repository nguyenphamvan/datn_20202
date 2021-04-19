package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    @GetMapping("/admin")
    public String adminHomePage() {
        return "admin/dashboard";
    }
}
