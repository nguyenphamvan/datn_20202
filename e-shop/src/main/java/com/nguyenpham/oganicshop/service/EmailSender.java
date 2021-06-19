package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailSender {

    void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
    void sendEmailOrderSuccess(String recipientEmail, Order order) throws MessagingException, UnsupportedEncodingException;
}
