package com.ticketbooking.notification.infrastructure.email;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${notification.mail.from}")
    private String from;

    @Value("${notification.mail.enabled:false}")
    private boolean enabled;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send an HTML email. Silently logs and swallows on failure so a bad SMTP
     * config never crashes the notification listener.
     */
    public void send(String to, String subject, String htmlBody) {
        if (!enabled) {
            log.info("[EMAIL DISABLED] Would send to={} subject=\"{}\"", to, subject);
            return;
        }
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(msg);
            log.info("[EMAIL SENT] to={} subject=\"{}\"", to, subject);
        } catch (Exception ex) {
            log.error("[EMAIL FAILED] to={} subject=\"{}\" error={}", to, subject, ex.getMessage());
        }
    }
}
