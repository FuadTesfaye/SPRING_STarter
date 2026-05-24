const AUTH_URL       = 'http://localhost:8080/api/auth';
const ORDER_URL      = 'http://localhost:8081/api/orders';
const PRODUCT_URL    = 'http://localhost:8086/api/products';
const INVENTORY_URL  = 'http://localhost:8083/api/inventory';
const PAYMENT_URL    = 'http://localhost:8082/api/payments';
const SHIPPING_URL   = 'http://localhost:8084/api/shipping';
const NOTIFICATION_URL = 'http://localhost:8085/api/notifications';

/* ── Helpers ─────────────────────────────────────────────── */
const getToken = () => localStorage.getItem('token');

const authHeaders = () => {
  const token = getToken();
  return {
    'Content-Type': 'application/json',
    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
  };
};

const handleResponse = async (res) => {
  if (!res.ok) {
    const text = await res.text().catch(() => res.statusText);
    throw new Error(text || `HTTP ${res.status}`);
  }
  const contentType = res.headers.get('content-type') || '';
  if (contentType.includes('application/json')) return res.json();
  return res.text();
};

const fetchWithTimeout = (url, options = {}, timeout = 8000) => {
  const controller = new AbortController();
  const id = setTimeout(() => controller.abort(), timeout);
  return fetch(url, { ...options, signal: controller.signal })
    .finally(() => clearTimeout(id));
};

/* ── API ─────────────────────────────────────────────────── */
export const api = {
  /* AUTH */
  login: async (credentials) => {
    const res = await fetchWithTimeout(`${AUTH_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(credentials),
    });
    return handleResponse(res);
  },

  register: async (userData) => {
    const res = await fetchWithTimeout(`${AUTH_URL}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData),
    });
    return handleResponse(res);
  },

  /* PRODUCTS */
  getProducts: async () => {
    const res = await fetchWithTimeout(PRODUCT_URL, { headers: authHeaders() });
    return handleResponse(res);
  },

  getProductById: async (id) => {
    const res = await fetchWithTimeout(`${PRODUCT_URL}/${id}`, { headers: authHeaders() });
    return handleResponse(res);
  },

  addProduct: async (productData) => {
    const res = await fetchWithTimeout(PRODUCT_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(productData),
    });
    return handleResponse(res);
  },

  updateProduct: async (id, productData) => {
    const res = await fetchWithTimeout(`${PRODUCT_URL}/${id}`, {
      method: 'PUT',
      headers: authHeaders(),
      body: JSON.stringify(productData),
    });
    return handleResponse(res);
  },

  deleteProduct: async (id) => {
    const res = await fetchWithTimeout(`${PRODUCT_URL}/${id}`, {
      method: 'DELETE',
      headers: authHeaders(),
    });
    return handleResponse(res);
  },

  /* ORDERS */
  createOrder: async (orderData) => {
    const res = await fetchWithTimeout(ORDER_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(orderData),
    });
    return handleResponse(res);
  },

  getOrders: async () => {
    const res = await fetchWithTimeout(ORDER_URL, { headers: authHeaders() });
    return handleResponse(res);
  },

  getOrdersByCustomerId: async (customerId) => {
    const res = await fetchWithTimeout(`${ORDER_URL}/customer/${customerId}`, {
      headers: authHeaders(),
    });
    return handleResponse(res);
  },

  /* INVENTORY */
  getInventory: async () => {
    const res = await fetchWithTimeout(INVENTORY_URL, { headers: authHeaders() });
    return handleResponse(res);
  },

  updateInventory: async (stockData) => {
    const res = await fetchWithTimeout(INVENTORY_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(stockData),
    });
    return handleResponse(res);
  },

  reserveInventory: async (items) => {
    const res = await fetchWithTimeout(`${INVENTORY_URL}/reserve`, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(items),
    });
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || 'Inventory reservation failed');
    }
    return res.text();
  },

  /* PAYMENTS */
  processPayment: async (paymentData) => {
    const res = await fetchWithTimeout(PAYMENT_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(paymentData),
    });
    return handleResponse(res);
  },

  getPaymentByOrderId: async (orderId) => {
    const res = await fetchWithTimeout(`${PAYMENT_URL}/order/${orderId}`, {
      headers: authHeaders(),
    });
    if (!res.ok) return null;
    return res.json();
  },

  /* SHIPPING */
  createShipment: async (shippingData) => {
    const res = await fetchWithTimeout(SHIPPING_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(shippingData),
    });
    return handleResponse(res);
  },

  getShipmentByOrderId: async (orderId) => {
    const res = await fetchWithTimeout(`${SHIPPING_URL}/order/${orderId}`, {
      headers: authHeaders(),
    });
    if (!res.ok) return null;
    return res.json();
  },

  /* NOTIFICATIONS */
  getNotifications: async () => {
    const res = await fetchWithTimeout(NOTIFICATION_URL, { headers: authHeaders() });
    return handleResponse(res);
  },

  createNotification: async (notificationData) => {
    const res = await fetchWithTimeout(NOTIFICATION_URL, {
      method: 'POST',
      headers: authHeaders(),
      body: JSON.stringify(notificationData),
    });
    return res.text();
  },
};
