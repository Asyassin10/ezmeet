package com.example.EzMeet.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.EzMeet.Models.User;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

   
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender,SpringTemplateEngine templateEngine) {
		super();
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	
	public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("🎉 Bienvenue sur EzMeet !");

             Context context = new Context();
            context.setVariable("firstName", user.getFirst_name());
            context.setVariable("name", user.getName());

            context.setVariable("loginLink", "https://ezmeet.com/login");

            String htmlContent = templateEngine.process("email/welcome", context);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
