package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmailListener {

    private final EmailService emailService;

    public VerificationEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "verification-email-queue")
    public void processMessage(String message) {
        String[] parts = message.split(";");
        String email = parts[0];
        String token = parts[1];
        emailService.sendVerificationEmail(email, token);
    }
}
