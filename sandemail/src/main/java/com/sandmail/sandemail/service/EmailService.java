package com.sandmail.sandemail.service;

import com.sandmail.sandemail.dto.UserRegisteredDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${app.subject}")
    private String subject;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(UserRegisteredDto register) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(register.email());
        message.setSubject(subject);
        message.setText(register.activationLink());
        mailSender.send(message);
    }
}
