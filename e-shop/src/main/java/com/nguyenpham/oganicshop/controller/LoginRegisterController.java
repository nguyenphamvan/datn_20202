package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token,  Model model) {
        User user = userService.getUserByResetToken(token);
        model.addAttribute("token", token);
        if (user == null) {
            return "user/message";
        }
        return "user/reset_password_form";
    }

    @GetMapping("/verifyAccount")
    public String verifyAccount(@Param("code") String code) {
        boolean verified = userService.verify(code);
        if (verified) {
            return "redirect:/login";
        } else {
            return "redirect:/403";
        }
    }
}
