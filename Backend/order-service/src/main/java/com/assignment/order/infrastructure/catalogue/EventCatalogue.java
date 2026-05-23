package com.assignment.order.infrastructure.catalogue;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * In-memory event catalogue — gives the order-service enough event context
 * to derive productName and price from an eventId, and to serve the
 * frontend's GET /orders/events endpoint without a separate event-service.
 */
@Component
public class EventCatalogue {

    public record EventInfo(
        String id,
        String title,
        String description,
        String venue,
        String category,
        String eventDate,
        BigDecimal ticketPrice,
        int totalSeats,
        int availableSeats,
        String imageUrl
    ) {}

    private final Map<String, EventInfo> catalogue = new LinkedHashMap<>();

    public EventCatalogue() {
        add("11111111-1111-1111-1111-111111111111",
            "Coldplay World Tour 2025", "An unforgettable night of music and lights.",
            "Wembley Stadium, London", "CONCERT", "2025-08-15T20:00:00",
            new BigDecimal("149.99"), 80000, 12450,
            "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=800");

        add("22222222-2222-2222-2222-222222222222",
            "UEFA Champions League Final", "Europe's biggest club match.",
            "Allianz Arena, Munich", "SPORTS", "2025-06-01T20:45:00",
            new BigDecimal("299.00"), 75000, 3200,
            "https://images.unsplash.com/photo-1508098682722-e99c643a7d19?w=800");

        add("33333333-3333-3333-3333-333333333333",
            "Hamilton — The Musical", "The award-winning Broadway phenomenon.",
            "Victoria Palace Theatre, London", "THEATRE", "2025-07-20T19:30:00",
            new BigDecimal("89.50"), 1500, 240,
            "https://images.unsplash.com/photo-1507924538820-ede94a04019d?w=800");

        add("44444444-4444-4444-4444-444444444444",
            "Glastonbury Festival 2025", "The world's most iconic music festival.",
            "Worthy Farm, Pilton", "FESTIVAL", "2025-06-25T12:00:00",
            new BigDecimal("340.00"), 200000, 8700,
            "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?w=800");

        add("55555555-5555-5555-5555-555555555555",
            "AWS re:Invent 2025", "The world's largest cloud computing conference.",
            "Venetian Convention Center, Las Vegas", "CONFERENCE", "2025-12-01T09:00:00",
            new BigDecimal("1899.00"), 50000, 21500,
            "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800");

        add("66666666-6666-6666-6666-666666666666",
            "Beyoncé Renaissance World Tour", "The tour everyone is talking about.",
            "Madison Square Garden, New York", "CONCERT", "2025-09-10T21:00:00",
            new BigDecimal("220.00"), 20000, 950,
            "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=800");
    }

    private void add(String id, String title, String desc, String venue, String cat,
                     String date, BigDecimal price, int total, int available, String img) {
        catalogue.put(id, new EventInfo(id, title, desc, venue, cat, date, price, total, available, img));
    }

    public List<EventInfo> findAll(String category) {
        if (category == null || category.isBlank() || category.equals("ALL")) {
            return new ArrayList<>(catalogue.values());
        }
        return catalogue.values().stream()
            .filter(e -> e.category().equalsIgnoreCase(category))
            .toList();
    }

    public Optional<EventInfo> findById(String id) {
        return Optional.ofNullable(catalogue.get(id));
    }
}
