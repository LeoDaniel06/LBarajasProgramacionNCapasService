package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String token) {

        String link = "http://localhost:8080/api/usuario/verificacion/confirmar?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Verifica tu cuenta");
            String htmlContent = getVerificationEmailTemplate(email, link);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Correo de verificación enviado a: " + email);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo de verificación", e);
        }
    }
    
    private String getVerificationEmailTemplate(String email, String link) {

        String template = """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; background: white; margin: auto; padding: 20px; border-radius: 10px;">

                    <h2 style="color: #4CAF50; text-align:center;">¡Bienvenido!</h2>

                    <p>Hola, <strong>%s</strong> 👋</p>

                    <p>Gracias por registrarte. Para activar tu cuenta, haz clic en el siguiente botón:</p>

                    <div style="text-align:center; margin: 30px 0;">
                        <a href="%s"
                           style="background-color:#4CAF50; color:white; padding: 12px 25px;
                                  text-decoration:none; border-radius: 5px;">
                            Verificar cuenta
                        </a>
                    </div>

                    <p>O copia y pega este enlace en tu navegador:</p>

                    <p style="word-break: break-all; color:#555;">%s</p>

                    <hr style="margin-top: 30px;">

                    <p style="color:#888; font-size:12px; text-align:center;">
                        Si no solicitaste esta verificación, puedes ignorar este correo.
                    </p>

                </div>
            </body>
            </html>
            """;

        return String.format(template, email, link, link);
    }
}