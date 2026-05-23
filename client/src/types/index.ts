export interface User {
  id: string;
  email: string;
  fullName: string;
  role: string;
}

export interface AuthResponse {
  token: string;
  userId: string;
  email: string;
  fullName: string;
  role: string;
}

export interface TicketEvent {
  id: string;
  title: string;
  description: string;
  venue: string;
  category: string;
  eventDate: string;
  ticketPrice: number;
  totalSeats: number;
  availableSeats: number;
  imageUrl?: string;
  createdAt: string;
}

export interface Booking {
  id: string;
  userId: string;
  eventId: string;
  quantity: number;
  totalAmount: number;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED';
  createdAt: string;
}

export interface Payment {
  id: string;
  bookingId: string;
  userId: string;
  amount: number;
  status: 'PROCESSING' | 'COMPLETED' | 'FAILED';
  paymentMethod: string;
  createdAt: string;
}

export interface Seat {
  id: string;
  eventId: string;
  seatNumber: string;
  row: string;
  section: string;
  status: 'AVAILABLE' | 'RESERVED' | 'SOLD';
  bookingId?: string;
}

export type EventCategory = 'ALL' | 'CONCERT' | 'SPORTS' | 'THEATRE' | 'FESTIVAL' | 'CONFERENCE';
