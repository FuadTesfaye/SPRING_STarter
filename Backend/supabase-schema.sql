-- Event Ticket Booking Platform — Supabase Schema
-- Run this in the Supabase SQL editor

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name TEXT NOT NULL,
    role TEXT NOT NULL DEFAULT 'USER',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS ticket_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    description TEXT,
    venue TEXT NOT NULL,
    category TEXT NOT NULL,
    event_date TIMESTAMPTZ NOT NULL,
    ticket_price DECIMAL(10,2) NOT NULL,
    total_seats INTEGER NOT NULL,
    available_seats INTEGER NOT NULL,
    image_url TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS bookings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    event_id UUID NOT NULL REFERENCES ticket_events(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    total_amount DECIMAL(10,2) NOT NULL,
    status TEXT NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    booking_id UUID NOT NULL REFERENCES bookings(id),
    user_id UUID NOT NULL REFERENCES users(id),
    amount DECIMAL(10,2) NOT NULL,
    status TEXT NOT NULL DEFAULT 'PROCESSING',
    payment_method TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS seats (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL REFERENCES ticket_events(id),
    seat_number TEXT NOT NULL,
    row TEXT NOT NULL DEFAULT 'A',
    section TEXT NOT NULL DEFAULT 'GENERAL',
    status TEXT NOT NULL DEFAULT 'AVAILABLE',
    booking_id UUID REFERENCES bookings(id),
    reserved_at TIMESTAMPTZ,
    UNIQUE(event_id, seat_number)
);

CREATE INDEX IF NOT EXISTS idx_bookings_user_id ON bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_event_id ON bookings(event_id);
CREATE INDEX IF NOT EXISTS idx_payments_booking_id ON payments(booking_id);
CREATE INDEX IF NOT EXISTS idx_payments_user_id ON payments(user_id);
CREATE INDEX IF NOT EXISTS idx_seats_event_id ON seats(event_id);
CREATE INDEX IF NOT EXISTS idx_ticket_events_category ON ticket_events(category);

ALTER TABLE users DISABLE ROW LEVEL SECURITY;
ALTER TABLE ticket_events DISABLE ROW LEVEL SECURITY;
ALTER TABLE bookings DISABLE ROW LEVEL SECURITY;
ALTER TABLE payments DISABLE ROW LEVEL SECURITY;
ALTER TABLE seats DISABLE ROW LEVEL SECURITY;

-- Sample events
INSERT INTO ticket_events (id, title, description, venue, category, event_date, ticket_price, total_seats, available_seats, image_url)
VALUES
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Taylor Swift — The Eras Tour', 'The most anticipated concert of the decade spanning all eras of Taylor''s legendary career.', 'SoFi Stadium, Los Angeles', 'CONCERT', NOW() + INTERVAL '30 days', 149.99, 500, 500, 'https://images.unsplash.com/photo-1540039155733-5bb30b4c61ad?w=800'),
    ('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'NBA Finals Game 7', 'The ultimate championship showdown. Winner takes all.', 'Chase Center, San Francisco', 'SPORTS', NOW() + INTERVAL '15 days', 299.99, 300, 300, 'https://images.unsplash.com/photo-1546519638-68e109498ffc?w=800'),
    ('c3d4e5f6-a7b8-9012-cdef-123456789012', 'Hamilton — Broadway Musical', 'The Tony Award-winning musical phenomenon.', 'Richard Rodgers Theatre, New York', 'THEATRE', NOW() + INTERVAL '7 days', 199.99, 200, 200, 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800'),
    ('d4e5f6a7-b8c9-0123-defa-234567890123', 'Coachella Valley Music Festival', 'Three days of non-stop music across multiple stages.', 'Empire Polo Club, Indio', 'FESTIVAL', NOW() + INTERVAL '60 days', 399.99, 1000, 1000, 'https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?w=800'),
    ('e5f6a7b8-c9d0-1234-efab-345678901234', 'Formula 1 Grand Prix', 'Experience the pinnacle of motorsport.', 'Las Vegas Strip Circuit', 'SPORTS', NOW() + INTERVAL '45 days', 599.99, 800, 800, 'https://images.unsplash.com/photo-1504707748692-419802cf939d?w=800'),
    ('f6a7b8c9-d0e1-2345-fabc-456789012345', 'Ed Sheeran Mathematics Tour', 'An intimate yet epic solo performance.', 'Madison Square Garden, New York', 'CONCERT', NOW() + INTERVAL '20 days', 89.99, 400, 400, 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=800')
ON CONFLICT (id) DO NOTHING;
