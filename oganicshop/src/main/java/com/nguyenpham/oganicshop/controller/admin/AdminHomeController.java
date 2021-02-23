package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    @GetMapping("/admin")
    public String adminHomePage() {
        return "admin/admin_home";
    }
}
