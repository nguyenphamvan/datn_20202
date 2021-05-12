package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;
import com.nguyenpham.oganicshop.service.EmailSender;
import com.nguyenpham.oganicshop.service.UserService;
import com.nguyenpham.oganicshop.util.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class ResetPasswordControllerApi {

    private UserService userService;
    private EmailSender emailSender;

    @Autowired
    public ResetPasswordControllerApi(UserService userService, EmailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @PostMapping("/api/forgot_password")
    public boolean processForgotPassword(HttpServletRequest request, @RequestBody ObjectNode object) throws UserNotFoundException, UnsupportedEncodingException, MessagingException {
        String email = object.get("email").asText();
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utils.getSiteURL(request) + "/reset_password?token=" + token;
            emailSender.sendEmail(email, resetPasswordLink);
            return true;
        } catch (UserNotFoundException ex) {
            return false;
        } catch (UnsupportedEncodingException | MessagingException e) {
           return false;
        }
    }

    @PostMapping("/api/reset_password")
    public boolean processResetPassword(@RequestBody ObjectNode object) {
        String token = object.get("token").asText();
        String password = object.get("password").asText();
        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            return false;
        } else {
            userService.updatePassword(user, password);
            return true;
        }
    }
}
