package com.ChasingtheLight.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送注册验证邮件
     * @param to 收件人邮箱
     * @param username 用户名
     * @param activationCode 激活码
     */
    public void sendActivationEmail(String to, String username, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("【寻光】账号激活");

        // 激活链接，实际应用中应使用前端页面链接
        String activationUrl = "http://localhost:8080/user/activate?code=" + activationCode;

        message.setText("亲爱的" + username + "，您好！\n\n" +
                "感谢您注册寻光摄影交流平台，请点击以下链接激活您的账号：\n" +
                activationUrl + "\n\n" +
                "如果您没有注册过本平台，请忽略此邮件。");

        mailSender.send(message);
    }
}