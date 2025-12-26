package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service;

import static com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Configuration.JmsConstants.VERIFICATION_QUEUE;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmailProducer {

    private final JmsTemplate jmsTemplate;

    public VerificationEmailProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendVerificationEmail(String email, String token) {
        String message = email + ";" + token;
        jmsTemplate.convertAndSend(VERIFICATION_QUEUE, message);
    }
}