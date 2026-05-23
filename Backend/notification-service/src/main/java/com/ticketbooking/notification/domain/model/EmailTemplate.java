package com.ticketbooking.notification.domain.model;

/**
 * Pure domain class — builds HTML email bodies with zero framework dependencies.
 */
public class EmailTemplate {

    private static final String BASE = """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="UTF-8"/>
          <style>
            body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #0f0f1a; margin: 0; padding: 0; }
            .wrapper { max-width: 600px; margin: 40px auto; background: #1a1a2e; border-radius: 16px; overflow: hidden; }
            .header  { background: linear-gradient(135deg, #7c3aed, #db2777); padding: 32px 40px; text-align: center; }
            .header h1 { color: #fff; margin: 0; font-size: 24px; letter-spacing: 1px; }
            .header p  { color: rgba(255,255,255,.7); margin: 6px 0 0; font-size: 14px; }
            .body    { padding: 36px 40px; color: #e2e8f0; }
            .body h2 { font-size: 20px; margin: 0 0 12px; color: #fff; }
            .body p  { font-size: 15px; line-height: 1.7; color: #a0aec0; margin: 0 0 12px; }
            .card    { background: rgba(255,255,255,.05); border: 1px solid rgba(255,255,255,.1);
                       border-radius: 12px; padding: 20px 24px; margin: 20px 0; }
            .card .row { display: flex; justify-content: space-between; padding: 6px 0;
                         border-bottom: 1px solid rgba(255,255,255,.06); font-size: 14px; }
            .card .row:last-child { border-bottom: none; }
            .card .label { color: #718096; }
            .card .value { color: #e2e8f0; font-weight: 600; }
            .btn { display: inline-block; margin: 20px 0 0; padding: 14px 32px;
                   background: linear-gradient(135deg,#7c3aed,#db2777); color: #fff !important;
                   border-radius: 8px; text-decoration: none; font-weight: 700; font-size: 15px; }
            .footer { text-align: center; padding: 20px 40px; color: #4a5568; font-size: 12px;
                      border-top: 1px solid rgba(255,255,255,.06); }
          </style>
        </head>
        <body>
          <div class="wrapper">
            <div class="header">
              <h1>🎟️ TicketHub</h1>
              <p>Your Event Ticketing Platform</p>
            </div>
            <div class="body">
              %s
            </div>
            <div class="footer">© 2025 TicketHub · You received this because you have an account with us.</div>
          </div>
        </body>
        </html>
        """;

    public static String welcome(String fullName, String email) {
        String body = """
            <h2>Welcome, %s! 🎉</h2>
            <p>Your TicketHub account is ready. Start browsing thousands of live events — concerts, sports, theatre, and more.</p>
            <div class="card">
              <div class="row"><span class="label">Name</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Email</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Status</span><span class="value">✅ Verified</span></div>
            </div>
            <p>Head over to TicketHub and grab your first ticket!</p>
            """.formatted(fullName, fullName, email);
        return BASE.formatted(body);
    }

    public static String orderConfirmation(String fullName, String orderId,
                                           String eventTitle, int quantity,
                                           String totalAmount) {
        String body = """
            <h2>Order Confirmed! 🎫</h2>
            <p>Hi %s, your ticket order has been placed and is being processed.</p>
            <div class="card">
              <div class="row"><span class="label">Order ID</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Event</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Tickets</span><span class="value">%d</span></div>
              <div class="row"><span class="label">Total</span><span class="value">$%s</span></div>
              <div class="row"><span class="label">Status</span><span class="value">⏳ Processing payment…</span></div>
            </div>
            <p>We'll send you another email once payment is confirmed.</p>
            """.formatted(fullName, orderId, eventTitle, quantity, totalAmount);
        return BASE.formatted(body);
    }

    public static String paymentConfirmed(String orderId, String amount) {
        String body = """
            <h2>Payment Confirmed ✅</h2>
            <p>Great news! Your payment has been successfully processed.</p>
            <div class="card">
              <div class="row"><span class="label">Order ID</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Amount Charged</span><span class="value">$%s</span></div>
              <div class="row"><span class="label">Status</span><span class="value">✅ Completed</span></div>
            </div>
            <p>Your tickets are being prepared. You'll receive a shipment confirmation shortly!</p>
            """.formatted(orderId, amount);
        return BASE.formatted(body);
    }

    public static String paymentFailed(String orderId, String reason) {
        String body = """
            <h2>Payment Failed ❌</h2>
            <p>Unfortunately your payment could not be processed.</p>
            <div class="card">
              <div class="row"><span class="label">Order ID</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Reason</span><span class="value">%s</span></div>
            </div>
            <p>Please try again with a different payment method.</p>
            """.formatted(orderId, reason);
        return BASE.formatted(body);
    }

    public static String itemDelivered(String fullName, String orderId,
                                       String productName, int quantity, double total) {
        String body = """
            <h2>Your Order Has Been Delivered! 🎉</h2>
            <p>Hi %s, great news — your TicketHub Shop order has been delivered!</p>
            <div class="card">
              <div class="row"><span class="label">Order ID</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Item</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Quantity</span><span class="value">%d</span></div>
              <div class="row"><span class="label">Total Paid</span><span class="value">$%.2f</span></div>
              <div class="row"><span class="label">Status</span><span class="value">✅ Delivered</span></div>
            </div>
            <p>Thank you for shopping with TicketHub. We hope you enjoy your purchase!</p>
            """.formatted(fullName, orderId, productName, quantity, total);
        return BASE.formatted(body);
    }

    public static String ticketsShipped(String orderId, String trackingNumber,
                                        String productName, int quantity) {
        String body = """
            <h2>Your Tickets Are On Their Way! 📦</h2>
            <p>Your ticket order has been dispatched.</p>
            <div class="card">
              <div class="row"><span class="label">Order ID</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Event</span><span class="value">%s</span></div>
              <div class="row"><span class="label">Quantity</span><span class="value">%d tickets</span></div>
              <div class="row"><span class="label">Tracking #</span><span class="value">%s</span></div>
            </div>
            <p>Enjoy the event! 🎉</p>
            """.formatted(orderId, productName, quantity, trackingNumber);
        return BASE.formatted(body);
    }
}
