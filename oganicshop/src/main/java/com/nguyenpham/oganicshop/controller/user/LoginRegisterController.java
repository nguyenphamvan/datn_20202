package com.nguyenpham.oganicshop.controller.user;

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

    @GetMapping("/login")
    public String loginPage() {
        return "user/signin";
    }

    @GetMapping("/signup")
    public String viewRegisterPage() {
        return "user/signup";
    }

    @GetMapping("/logout_success")
    public String logoutSuccessfulPage() {
        return "redirect:/";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "user/forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token,  Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (user == null) {
            return "user/message";
        }
        return "user/reset_password_form";
    }
}