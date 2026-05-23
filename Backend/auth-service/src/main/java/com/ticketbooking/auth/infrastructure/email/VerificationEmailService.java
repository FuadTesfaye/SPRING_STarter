package com.ticketbooking.auth.infrastructure.email;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class VerificationEmailService {

    private static final Logger log = LoggerFactory.getLogger(VerificationEmailService.class);

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Value("${app.base-url:http://localhost:8081/api}")
    private String baseUrl;

    public VerificationEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerification(String to, String fullName, String token) {
        String link = baseUrl + "/auth/verify?token=" + token;
        String html = buildHtml(fullName, link);
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Confirm your TicketHub account");
            helper.setText(html, true);
            mailSender.send(msg);
            log.info("[EMAIL SENT] Verification email to={}", to);
        } catch (Exception ex) {
            log.error("[EMAIL FAILED] Verification email to={} error={}", to, ex.getMessage());
        }
    }

    private String buildHtml(String fullName, String link) {
        return """
            <!DOCTYPE html>
            <html>
            <body style="margin:0;padding:0;background:#0f0f1a;font-family:'Segoe UI',Arial,sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0">
                <tr><td align="center" style="padding:40px 20px;">
                  <table width="560" cellpadding="0" cellspacing="0"
                         style="background:rgba(255,255,255,0.05);border:1px solid rgba(255,255,255,0.1);
                                border-radius:16px;overflow:hidden;">
                    <tr>
                      <td style="background:linear-gradient(135deg,#7c3aed,#db2777);padding:32px;text-align:center;">
                        <h1 style="margin:0;color:#fff;font-size:28px;font-weight:900;">🎫 TicketHub</h1>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:40px 32px;">
                        <h2 style="margin:0 0 16px;color:#fff;font-size:22px;">Confirm your email address</h2>
                        <p style="margin:0 0 12px;color:rgba(255,255,255,0.7);font-size:15px;line-height:1.6;">
                          Hi <strong style="color:#fff;">%s</strong>,
                        </p>
                        <p style="margin:0 0 32px;color:rgba(255,255,255,0.7);font-size:15px;line-height:1.6;">
                          Thanks for signing up! Click the button below to verify your email address
                          and activate your account. This link expires in <strong style="color:#fff;">24 hours</strong>.
                        </p>
                        <div style="text-align:center;margin-bottom:32px;">
                          <a href="%s"
                             style="display:inline-block;background:linear-gradient(135deg,#7c3aed,#db2777);
                                    color:#fff;text-decoration:none;font-weight:700;font-size:16px;
                                    padding:16px 40px;border-radius:12px;">
                            Verify Email Address
                          </a>
                        </div>
                        <p style="margin:0;color:rgba(255,255,255,0.4);font-size:12px;line-height:1.6;">
                          If you didn't create an account, you can safely ignore this email.<br>
                          Or copy this link: <span style="color:#a78bfa;">%s</span>
                        </p>
                      </td>
                    </tr>
                    <tr>
                      <td style="padding:20px 32px;border-top:1px solid rgba(255,255,255,0.08);
                                 text-align:center;color:rgba(255,255,255,0.3);font-size:12px;">
                        &copy; 2024 TicketHub. All rights reserved.
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </body>
            </html>
            """.formatted(fullName, link, link);
    }
}
