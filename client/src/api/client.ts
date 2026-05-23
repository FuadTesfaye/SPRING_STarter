import axios from 'axios';

// Auth Service  → 8081
const AUTH_URL    = import.meta.env.VITE_AUTH_SERVICE_URL    || 'http://localhost:8081/api';
// Order Service → 8082  (handles events, orders/bookings)
const ORDER_URL   = import.meta.env.VITE_ORDER_SERVICE_URL   || 'http://localhost:8082/api';
// Event URL aliases to the same order-service
const EVENT_URL   = import.meta.env.VITE_EVENT_SERVICE_URL   || 'http://localhost:8082/api';
const BOOKING_URL = import.meta.env.VITE_BOOKING_SERVICE_URL || 'http://localhost:8082/api';

function getToken() {
  return localStorage.getItem('token');
}

function authHeaders() {
  const token  = getToken();
  const userId = localStorage.getItem('userId');
  return {
    ...(token  ? { Authorization: `Bearer ${token}` } : {}),
    ...(userId ? { 'X-User-Id': userId }              : {}),
  };
}

export const authApi = {
  register: (email: string, password: string, fullName: string) =>
    axios.post(`${AUTH_URL}/auth/register`, { email, password, fullName }),
  login: (email: string, password: string) =>
    axios.post(`${AUTH_URL}/auth/login`, { email, password }),
};

export const eventApi = {
  list: (category?: string) =>
    axios.get(`${EVENT_URL}/events`, {
      params: category && category !== 'ALL' ? { category } : undefined,
    }),
  get: (id: string) =>
    axios.get(`${EVENT_URL}/events/${id}`),
};

/**
 * bookingApi.create posts to /bookings on the order-service.
 * The backend derives ticket price from the event catalogue — no price needed from frontend.
 * Payment + inventory are triggered automatically via RabbitMQ after order creation.
 */
export const bookingApi = {
  create: (eventId: string, quantity: number) => {
    const user = (() => { try { return JSON.parse(localStorage.getItem('user') || 'null'); } catch { return null; } })();
    return axios.post(`${BOOKING_URL}/bookings`, { eventId, quantity }, {
      headers: {
        ...authHeaders(),
        ...(user?.email ? { 'X-User-Email': user.email } : {}),
        ...(user?.fullName ? { 'X-User-Name': user.fullName } : {}),
      },
    });
  },
  myBookings: () =>
    axios.get(`${BOOKING_URL}/bookings/my`, { headers: authHeaders() }),
};

/**
 * paymentApi is kept for interface compatibility but payment now fires via RabbitMQ.
 * Calling process() returns a synthetic success response so the UI can navigate to /payment/result.
 */
export const paymentApi = {
  process: (_bookingId: string, amount: number, _paymentMethod: string, _cardToken?: string) =>
    Promise.resolve({ data: { status: 'COMPLETED', amount } }),
};

/**
 * seatApi — seat-service is not in the assignment but the UI seat grid handles
 * 404s gracefully by falling back to randomly generated mock seats.
 */
export const seatApi = {
  list: (eventId: string) =>
    axios.get(`${ORDER_URL}/seats`, { params: { eventId } }),
  reserve: (_eventId: string, _bookingId: string, _seatNumber: string) =>
    Promise.resolve({ data: { status: 'RESERVED' } }),
};
