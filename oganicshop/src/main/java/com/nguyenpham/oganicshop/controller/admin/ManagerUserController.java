package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ManagerUserController {

    @GetMapping("/manager_users")
    public String viewManagerUsers() {
        return "admin/manager_users";
    }
}
