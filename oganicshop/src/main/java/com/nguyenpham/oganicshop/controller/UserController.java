package com.nguyenpham.oganicshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/my_account")
    public String myAccountPage() {
        return "account";
    }
}
