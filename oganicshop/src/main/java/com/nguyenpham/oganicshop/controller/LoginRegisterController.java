package com.nguyenpham.oganicshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRegisterController {

    @GetMapping("/login")
    public String loginPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String viewRegisterPage() {
        return "signup";
    }

    @GetMapping("/logout_success")
    public String logoutSuccessfulPage() {
        return "redirect:/";
    }
}
