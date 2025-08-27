package com.example.EzMeet.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.EzMeet.Models.EmailVerificationToken;
import com.example.EzMeet.Models.User;
import com.example.EzMeet.repository.EmailVerificationTokenRepository;
import com.example.EzMeet.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailVerificationService {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public EmailVerificationService(JavaMailSender mailSender,
                                    SpringTemplateEngine templateEngine,
                                    EmailVerificationTokenRepository tokenRepository,
                                    UserRepository userRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }
    
    public void sendVerificationEmail(User user) {
        try {
             String token = UUID.randomUUID().toString();

            EmailVerificationToken verificationToken = new EmailVerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            tokenRepository.save(verificationToken);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Email Verification - EzMeet");

            Context context = new Context();
            context.setVariable("name", user.getFirst_name());
            context.setVariable("verificationLink", "http://localhost:8080/verify-email?token=" + token);

            String html = templateEngine.process("email/verify-email", context);
            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
    
    public boolean verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);
        return true;
    }

}
