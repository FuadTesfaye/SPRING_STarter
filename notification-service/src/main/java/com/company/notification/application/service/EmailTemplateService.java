package com.company.notification.application.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String generateBackInStockEmail(String productId, String shade) {
        return """
            <table style="max-width: 600px; margin: 0 auto; font-family: 'DM Sans', sans-serif; background-color: #FAFAF8;">
                <tr><td style="padding: 20px; text-align: center; background-color: #1A1A2E;">
                    <h1 style="color: #C9A96E; font-family: 'Playfair Display', serif;">GlamHaven.</h1>
                </td></tr>
                <tr><td style="padding: 40px 20px; text-align: center;">
                    <h2 style="color: #1A1A2E;">Great News! It's Back.</h2>
                    <p style="color: #6B6B7B;">The item you've been waiting for is finally back in stock.</p>
                    <p style="color: #1A1A2E; font-weight: bold;">Product ID: %s | Shade: %s</p>
                    <a href="#" style="display: inline-block; padding: 15px 30px; background-color: #C9A96E; color: #1A1A2E; text-decoration: none; border-radius: 4px; font-weight: bold; margin-top: 20px;">Shop Now</a>
                </td></tr>
                <tr><td style="padding: 20px; text-align: center; border-top: 1px solid #E8E8EC;">
                    <p style="color: #9B9BAB; font-size: 12px;">You received this email because you subscribed to restock alerts.</p>
                    <a href="#" style="color: #9B9BAB; font-size: 12px;">Unsubscribe</a>
                </td></tr>
            </table>
            """.formatted(productId, shade);
    }

    public String generateTierUpgradedEmail(String tierName) {
        return """
            <table style="max-width: 600px; margin: 0 auto; font-family: 'DM Sans', sans-serif; background-color: #FAFAF8;">
                <tr><td style="padding: 20px; text-align: center; background-color: #1A1A2E;">
                    <h1 style="color: #C9A96E; font-family: 'Playfair Display', serif;">GlamHaven.</h1>
                </td></tr>
                <tr><td style="padding: 40px 20px; text-align: center;">
                    <h2 style="color: #1A1A2E;">Congratulations! You're now %s.</h2>
                    <p style="color: #6B6B7B;">You've unlocked a new tier and exclusive perks.</p>
                    <a href="#" style="display: inline-block; padding: 15px 30px; background-color: #C9A96E; color: #1A1A2E; text-decoration: none; border-radius: 4px; font-weight: bold; margin-top: 20px;">View My Perks</a>
                </td></tr>
            </table>
            """.formatted(tierName);
    }
}
