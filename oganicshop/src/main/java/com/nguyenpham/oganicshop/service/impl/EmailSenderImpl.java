package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import com.nguyenpham.oganicshop.entity.OrderItem;

@Service
public class EmailSenderImpl implements EmailSender {

    private JavaMailSender mailSender;

    @Autowired
    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String senderName = "Admin Web shop";
        String mailContent = "<p>Cảm ơn  <b>" + user.getFullName() + "</b>,</p>";
        String verifyURL = siteURL + "/verifyAccount?code=" + user.getVerificationCode();

        mailContent += "<p>Vui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn :</p>";
        mailContent += "<a href=\"" + verifyURL + "\">Xác nhận</a>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("nguyenphamvan1998@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    @Override
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String senderName = "Web shop support";
        String subject = "Here's the link to reset your password";

        String content = "<p>Chào bạn,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn</p>"
                + "<p><a href=\"" + link + "\">Thay đổi mật khẩu của tôi</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
                + "hoặc bạn đã không thực hiện yêu cầu.</p>";

        helper.setFrom("nguyenphamvan1998@gmail.com", senderName);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendEmailOrderSuccess(String recipientEmail, Order order) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String senderName = "Webshop DATN";
        String subject = "Thông tin đơn hàng đã đặt";

        String content = "<p><b>Thông tin đơn hàng</b></p>"
                + "<p>Mã đơn hàng : " + order.getId() + "</p>"
                + "<p>Giá trị đơn hàng : " + order.getTotal() + "</p>"
                + "<br>"
                + "<p>Đơn hàng gồm có<>"
                + "<ul>";
        for (OrderItem orderItem : order.getOrderItems()) {
            content += "<li>" +
                    "<p>" + orderItem.getProduct().getName() + "</p>" +
                    "<p>Đơn giá : " + orderItem.getProduct().getFinalPrice() + "</p>" +
                    "<p>Số lượng : " + orderItem.getQuantity() + "</p>" +
                    "</li>";
        }
        content += "</ul>" +
                "<p>Đăng nhập và theo dõi tình trạng đơn hàng của bạn tài website</p>";


        helper.setFrom("nguyenphamvan1998@gmail.com", senderName);
        helper.setTo("nguyenpersit4e@gmail.com");
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
