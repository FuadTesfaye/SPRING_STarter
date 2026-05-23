import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  ShoppingCart, X, Plus, Minus, Trash2, Package,
  Star, Truck, ShieldCheck, ArrowRight, Tag
} from 'lucide-react';
import { useAuthStore } from '../store';
import axios from 'axios';

const NOTIF_URL = import.meta.env.VITE_NOTIFICATION_SERVICE_URL || 'http://localhost:8086/api';

// ── Product catalogue ──────────────────────────────────────────
const PRODUCTS = [
  {
    id: 'shop-001',
    name: 'Pro Microphone XLR',
    category: 'Audio',
    price: 129.99,
    rating: 4.8,
    reviews: 342,
    badge: 'Best Seller',
    badgeColor: 'bg-amber-500/20 text-amber-300 border-amber-500/30',
    description: 'Studio-grade XLR condenser mic for recording and live performance.',
    image: 'https://images.unsplash.com/photo-1520170350707-b2da59970118?w=500',
    tags: ['Studio', 'Live', 'Recording'],
  },
  {
    id: 'shop-002',
    name: 'Official NBA Basketball',
    category: 'Sports',
    price: 49.99,
    rating: 4.7,
    reviews: 891,
    badge: 'Top Rated',
    badgeColor: 'bg-orange-500/20 text-orange-300 border-orange-500/30',
    description: 'Official size and weight basketball with premium leather cover.',
    image: 'https://images.unsplash.com/photo-1546519638405-a9f168ff4af4?w=500',
    tags: ['NBA', 'Official', 'Indoor'],
  },
  {
    id: 'shop-003',
    name: 'TicketHub Classic Tee',
    category: 'Apparel',
    price: 29.99,
    rating: 4.6,
    reviews: 512,
    badge: 'New',
    badgeColor: 'bg-green-500/20 text-green-300 border-green-500/30',
    description: 'Premium cotton unisex tee with embroidered TicketHub logo.',
    image: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500',
    tags: ['Cotton', 'Unisex', 'Limited'],
  },
  {
    id: 'shop-004',
    name: 'Wireless DJ Headphones',
    category: 'Audio',
    price: 199.99,
    rating: 4.9,
    reviews: 267,
    badge: 'Premium',
    badgeColor: 'bg-purple-500/20 text-purple-300 border-purple-500/30',
    description: '40hr battery, active noise cancelling, 360° spatial audio.',
    image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500',
    tags: ['Wireless', 'ANC', 'DJ'],
  },
  {
    id: 'shop-005',
    name: 'Football Cleats Pro',
    category: 'Sports',
    price: 89.99,
    rating: 4.5,
    reviews: 433,
    badge: null,
    badgeColor: '',
    description: 'High-traction synthetic leather cleats for all weather conditions.',
    image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500',
    tags: ['Football', 'Outdoor', 'Pro'],
  },
  {
    id: 'shop-006',
    name: 'Event Hoodie — Limited',
    category: 'Apparel',
    price: 64.99,
    rating: 4.8,
    reviews: 189,
    badge: 'Limited',
    badgeColor: 'bg-red-500/20 text-red-300 border-red-500/30',
    description: 'Heavyweight 400gsm fleece hoodie. Embroidered front, screen-printed back.',
    image: 'https://images.unsplash.com/photo-1556821840-3a63f15732ce?w=500',
    tags: ['Fleece', 'Limited', 'Unisex'],
  },
  {
    id: 'shop-007',
    name: 'Portable Speaker 360°',
    category: 'Audio',
    price: 79.99,
    rating: 4.7,
    reviews: 654,
    badge: 'Sale',
    badgeColor: 'bg-pink-500/20 text-pink-300 border-pink-500/30',
    description: 'Waterproof BT 5.3, 20hr battery, 360° omnidirectional sound.',
    image: 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500',
    tags: ['Bluetooth', 'Waterproof', 'Portable'],
  },
  {
    id: 'shop-008',
    name: 'Gym Training Shorts',
    category: 'Apparel',
    price: 34.99,
    rating: 4.4,
    reviews: 301,
    badge: null,
    badgeColor: '',
    description: 'Moisture-wicking quick-dry fabric with secure zip pocket.',
    image: 'https://images.unsplash.com/photo-1562886877-e95de4851dae?w=500',
    tags: ['Training', 'Dry-fit', 'Gym'],
  },
];

const CATEGORIES = ['All', 'Audio', 'Sports', 'Apparel'];

interface CartItem { product: typeof PRODUCTS[0]; quantity: number; }

// ── Cart sidebar ───────────────────────────────────────────────
function CartSidebar({
  cart, onClose, onQty, onRemove, onCheckout,
}: {
  cart: CartItem[];
  onClose: () => void;
  onQty: (id: string, delta: number) => void;
  onRemove: (id: string) => void;
  onCheckout: () => void;
}) {
  const subtotal = cart.reduce((s, i) => s + i.product.price * i.quantity, 0);
  const shipping = subtotal > 100 ? 0 : 9.99;
  const total = subtotal + shipping;

  return (
    <div className="fixed inset-0 z-50 flex">
      <div className="flex-1 bg-black/60 backdrop-blur-sm" onClick={onClose} />
      <div className="w-full max-w-sm bg-[#0d0d1f] border-l border-white/10 flex flex-col h-full slide-up">
        {/* Header */}
        <div className="flex items-center justify-between px-5 py-4 border-b border-white/10">
          <div className="flex items-center gap-2">
            <ShoppingCart className="w-5 h-5 text-purple-400" />
            <span className="font-bold text-white">Your Cart</span>
            <span className="text-xs bg-purple-500/20 text-purple-300 px-2 py-0.5 rounded-full">{cart.reduce((s, i) => s + i.quantity, 0)}</span>
          </div>
          <button onClick={onClose} className="w-8 h-8 rounded-full glass flex items-center justify-center hover:bg-white/10">
            <X className="w-4 h-4 text-white/60" />
          </button>
        </div>

        {/* Items */}
        <div className="flex-1 overflow-y-auto px-5 py-4 space-y-4">
          {cart.length === 0 && (
            <div className="text-center py-16 text-white/30">
              <ShoppingCart className="w-12 h-12 mx-auto mb-3 opacity-30" />
              <p>Your cart is empty</p>
            </div>
          )}
          {cart.map(({ product: p, quantity }) => (
            <div key={p.id} className="glass p-3 flex gap-3">
              <img src={p.image} alt={p.name} className="w-16 h-16 object-cover rounded-xl shrink-0" />
              <div className="flex-1 min-w-0">
                <div className="text-white text-sm font-semibold truncate">{p.name}</div>
                <div className="text-white/40 text-xs">{p.category}</div>
                <div className="flex items-center justify-between mt-2">
                  <div className="flex items-center gap-2">
                    <button onClick={() => onQty(p.id, -1)} className="w-6 h-6 rounded-full glass flex items-center justify-center hover:bg-white/10 text-white/60">
                      <Minus className="w-3 h-3" />
                    </button>
                    <span className="text-white text-sm font-bold w-4 text-center">{quantity}</span>
                    <button onClick={() => onQty(p.id, 1)} className="w-6 h-6 rounded-full glass flex items-center justify-center hover:bg-white/10 text-white/60">
                      <Plus className="w-3 h-3" />
                    </button>
                  </div>
                  <div className="flex items-center gap-2">
                    <span className="text-white font-bold text-sm">${(p.price * quantity).toFixed(2)}</span>
                    <button onClick={() => onRemove(p.id)} className="text-red-400/60 hover:text-red-400 transition-colors">
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Summary */}
        {cart.length > 0 && (
          <div className="px-5 py-4 border-t border-white/10 space-y-3">
            <div className="space-y-2 text-sm">
              <div className="flex justify-between text-white/50">
                <span>Subtotal</span><span>${subtotal.toFixed(2)}</span>
              </div>
              <div className="flex justify-between text-white/50">
                <span>Shipping</span>
                <span>{shipping === 0 ? <span className="text-emerald-400">Free</span> : `$${shipping.toFixed(2)}`}</span>
              </div>
              {shipping > 0 && <p className="text-white/25 text-xs">Free shipping on orders over $100</p>}
              <div className="flex justify-between text-white font-bold text-base pt-2 border-t border-white/10">
                <span>Total</span>
                <span className="gradient-text-gold">${total.toFixed(2)}</span>
              </div>
            </div>
            <button onClick={onCheckout} className="btn-primary w-full py-3.5 rounded-xl flex items-center justify-center gap-2">
              Checkout <ArrowRight className="w-4 h-4" />
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

// ── Checkout modal ─────────────────────────────────────────────
function CheckoutModal({
  cart, onClose, onSuccess,
}: {
  cart: CartItem[];
  onClose: () => void;
  onSuccess: (orderId: string, total: number) => void;
}) {
  const { user } = useAuthStore();
  const navigate = useNavigate();
  const [step, setStep] = useState<'shipping' | 'payment' | 'processing'>('shipping');
  const [form, setForm] = useState({ name: user?.fullName || '', address: '', city: '', zip: '', country: 'US' });
  const [error, setError] = useState('');

  const subtotal = cart.reduce((s, i) => s + i.product.price * i.quantity, 0);
  const shipping = subtotal > 100 ? 0 : 9.99;
  const total = subtotal + shipping;

  const field = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
    setForm(f => ({ ...f, [k]: e.target.value }));

  const handleOrder = async () => {
    setStep('processing');
    setError('');
    await new Promise(r => setTimeout(r, 1500));
    const orderId = 'SHOP-' + Math.random().toString(36).slice(2, 10).toUpperCase();
    const productSummary = cart.length === 1
      ? `${cart[0].product.name} ×${cart[0].quantity}`
      : `${cart.length} items`;
    // Register order in notification-service so admin can mark it delivered
    try {
      await axios.post(`${NOTIF_URL}/notifications/admin/orders`, {
        orderId,
        productName: productSummary,
        quantity: cart.reduce((s, i) => s + i.quantity, 0),
        totalAmount: total,
        email: user?.email ?? '',
        fullName: user?.fullName ?? '',
      }, { headers: { 'X-Admin-Key': 'philemondan32@gmail.com:Dantab@4040' } });
    } catch { /* non-critical — proceed regardless */ }
    onSuccess(orderId, total);
  };

  if (!user) {
    return (
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-sm">
        <div className="glass-strong p-8 max-w-sm w-full text-center">
          <Package className="w-12 h-12 text-purple-400 mx-auto mb-4" />
          <h3 className="text-xl font-bold text-white mb-2">Sign in to checkout</h3>
          <p className="text-white/40 mb-6">Create an account to complete your purchase.</p>
          <div className="flex gap-3">
            <button onClick={onClose} className="btn-secondary flex-1">Cancel</button>
            <button onClick={() => navigate('/login')} className="btn-primary flex-1">Sign In</button>
          </div>
        </div>
      </div>
    );
  }

  const steps = ['shipping', 'payment'];

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/70 backdrop-blur-md">
      <div className="glass-strong w-full sm:max-w-lg max-h-[92vh] overflow-y-auto rounded-t-3xl sm:rounded-3xl">
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-white/10 sticky top-0 glass-strong z-10 rounded-t-3xl">
          <h2 className="text-lg font-bold text-white">Checkout</h2>
          <button onClick={onClose} className="w-8 h-8 rounded-full glass flex items-center justify-center hover:bg-white/10">
            <X className="w-4 h-4 text-white/60" />
          </button>
        </div>

        <div className="p-6 space-y-6">
          {/* Step pills */}
          <div className="flex items-center gap-2">
            {steps.map((s, i) => (
              <div key={s} className="flex items-center gap-2">
                <div className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold transition-all ${
                  step === s || step === 'processing'
                    ? 'bg-gradient-to-br from-purple-500 to-pink-500 text-white'
                    : step === 'payment' && s === 'shipping'
                    ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/30'
                    : 'glass text-white/30'
                }`}>
                  {step === 'payment' && s === 'shipping' ? '✓' : i + 1}
                </div>
                <span className={`text-sm font-medium capitalize ${step === s ? 'text-white' : 'text-white/30'}`}>{s}</span>
                {i < steps.length - 1 && <ArrowRight className="w-3 h-3 text-white/20" />}
              </div>
            ))}
          </div>

          {/* Order summary */}
          <div className="glass p-4 space-y-2">
            <div className="text-xs text-white/40 font-medium uppercase tracking-wide mb-3">Order Summary</div>
            {cart.map(({ product: p, quantity }) => (
              <div key={p.id} className="flex justify-between text-sm">
                <span className="text-white/60 truncate">{p.name} × {quantity}</span>
                <span className="text-white font-medium ml-4">${(p.price * quantity).toFixed(2)}</span>
              </div>
            ))}
            <div className="border-t border-white/10 pt-2 mt-2 flex justify-between font-bold text-white">
              <span>Total</span>
              <span className="gradient-text-gold">${total.toFixed(2)}</span>
            </div>
          </div>

          {/* STEP 1: Shipping */}
          {step === 'shipping' && (
            <div className="space-y-4">
              <div className="flex items-center gap-2 text-white font-semibold">
                <Truck className="w-4 h-4 text-purple-400" /> Shipping Address
              </div>
              <div className="grid grid-cols-2 gap-3">
                <div className="col-span-2">
                  <label className="text-xs text-white/50 mb-1.5 block">Full Name</label>
                  <input value={form.name} onChange={field('name')} placeholder="John Doe" className="input-field" required />
                </div>
                <div className="col-span-2">
                  <label className="text-xs text-white/50 mb-1.5 block">Street Address</label>
                  <input value={form.address} onChange={field('address')} placeholder="123 Main St" className="input-field" required />
                </div>
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">City</label>
                  <input value={form.city} onChange={field('city')} placeholder="New York" className="input-field" required />
                </div>
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">ZIP Code</label>
                  <input value={form.zip} onChange={field('zip')} placeholder="10001" className="input-field" required />
                </div>
                <div className="col-span-2">
                  <label className="text-xs text-white/50 mb-1.5 block">Country</label>
                  <select value={form.country} onChange={field('country')} className="input-field">
                    {['US', 'UK', 'CA', 'AU', 'DE', 'FR', 'JP', 'SG'].map(c => (
                      <option key={c} value={c} style={{ background: '#0f1628' }}>{c}</option>
                    ))}
                  </select>
                </div>
              </div>
              <button
                onClick={() => { if (form.name && form.address && form.city && form.zip) setStep('payment'); }}
                className="btn-primary w-full py-3.5 rounded-xl flex items-center justify-center gap-2"
              >
                Continue to Payment <ArrowRight className="w-4 h-4" />
              </button>
            </div>
          )}

          {/* STEP 2: Payment */}
          {(step === 'payment' || step === 'processing') && (
            <div className="space-y-4">
              <div className="flex items-center gap-2 text-white font-semibold">
                <ShieldCheck className="w-4 h-4 text-purple-400" /> Payment
              </div>

              <div className="glass p-4 space-y-2 text-sm text-white/50">
                <div className="flex justify-between"><span>Ship to</span><span className="text-white">{form.name}, {form.city}</span></div>
                <div className="flex justify-between"><span>Address</span><span className="text-white">{form.address}, {form.zip}</span></div>
              </div>

              {/* Mock card UI */}
              <div className="glass p-4 space-y-3">
                <label className="text-xs text-white/50 block">Card Number</label>
                <input placeholder="4242 4242 4242 4242" className="input-field font-mono" disabled defaultValue="4242 4242 4242 4242" />
                <div className="grid grid-cols-2 gap-3">
                  <div>
                    <label className="text-xs text-white/50 block mb-1.5">Expiry</label>
                    <input placeholder="12/26" className="input-field font-mono" disabled defaultValue="12/26" />
                  </div>
                  <div>
                    <label className="text-xs text-white/50 block mb-1.5">CVV</label>
                    <input placeholder="•••" className="input-field font-mono" disabled defaultValue="•••" />
                  </div>
                </div>
                <p className="text-xs text-white/20">Demo mode — payment is simulated</p>
              </div>

              {error && (
                <div className="glass border border-red-500/30 bg-red-500/10 p-3 rounded-xl text-sm text-red-300">{error}</div>
              )}

              <div className="flex gap-3">
                <button onClick={() => setStep('shipping')} disabled={step === 'processing'} className="btn-secondary flex-1 py-3.5 rounded-xl">Back</button>
                <button onClick={handleOrder} disabled={step === 'processing'} className="btn-primary flex-1 py-3.5 rounded-xl flex items-center justify-center gap-2">
                  {step === 'processing'
                    ? <><div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" /> Processing...</>
                    : <>Place Order · ${total.toFixed(2)}</>
                  }
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

// ── Main Shop Page ─────────────────────────────────────────────
export default function ShopPage() {
  const navigate = useNavigate();
  const { user } = useAuthStore();
  const [category, setCategory] = useState('All');
  const [cart, setCart] = useState<CartItem[]>([]);
  const [cartOpen, setCartOpen] = useState(false);
  const [checkoutOpen, setCheckoutOpen] = useState(false);
  const [search, setSearch] = useState('');

  const cartKey = user ? `cart_${user.id}` : null;

  // Load cart from localStorage when user changes
  useEffect(() => {
    if (!cartKey) { setCart([]); return; }
    const saved = localStorage.getItem(cartKey);
    if (saved) {
      try {
        const ids: Record<string, number> = JSON.parse(saved);
        const restored = PRODUCTS
          .filter(p => ids[p.id])
          .map(p => ({ product: p, quantity: ids[p.id] }));
        setCart(restored);
      } catch { setCart([]); }
    } else {
      setCart([]);
    }
  }, [cartKey]);

  // Save cart to localStorage whenever it changes
  useEffect(() => {
    if (!cartKey) return;
    const ids: Record<string, number> = {};
    cart.forEach(i => { ids[i.product.id] = i.quantity; });
    localStorage.setItem(cartKey, JSON.stringify(ids));
  }, [cart, cartKey]);

  const cartCount = cart.reduce((s, i) => s + i.quantity, 0);

  const addToCart = (product: typeof PRODUCTS[0]) => {
    setCart(prev => {
      const existing = prev.find(i => i.product.id === product.id);
      if (existing) return prev.map(i => i.product.id === product.id ? { ...i, quantity: i.quantity + 1 } : i);
      return [...prev, { product, quantity: 1 }];
    });
  };

  const updateQty = (id: string, delta: number) => {
    setCart(prev => prev
      .map(i => i.product.id === id ? { ...i, quantity: i.quantity + delta } : i)
      .filter(i => i.quantity > 0)
    );
  };

  const removeFromCart = (id: string) => setCart(prev => prev.filter(i => i.product.id !== id));

  const handleSuccess = (orderId: string, total: number) => {
    setCheckoutOpen(false);
    setCart([]);
    if (cartKey) localStorage.removeItem(cartKey);
    navigate('/payment/result', {
      state: {
        status: 'success',
        booking: { id: orderId, quantity: cartCount, totalAmount: total, status: 'CONFIRMED', createdAt: new Date().toISOString() },
        event: { title: 'TicketHub Shop Order', venue: 'Delivered to your address', eventDate: new Date(Date.now() + 7 * 86400000).toISOString() },
        amount: total,
      },
    });
  };

  const filtered = PRODUCTS.filter(p =>
    (category === 'All' || p.category === category) &&
    (p.name.toLowerCase().includes(search.toLowerCase()) || p.category.toLowerCase().includes(search.toLowerCase()))
  );

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">

      {/* Page header */}
      <div className="flex items-start justify-between mb-8 slide-up">
        <div>
          <h1 className="text-4xl font-black text-white mb-2">Shop</h1>
          <p className="text-white/40">Gear, apparel, and accessories</p>
        </div>
        {/* Cart button */}
        <button
          onClick={() => setCartOpen(true)}
          className="relative glass px-4 py-2.5 rounded-xl flex items-center gap-2 hover:bg-white/10 transition-all"
        >
          <ShoppingCart className="w-5 h-5 text-white/70" />
          <span className="text-white/70 text-sm font-medium hidden sm:block">Cart</span>
          {cartCount > 0 && (
            <span className="absolute -top-2 -right-2 w-5 h-5 bg-gradient-to-br from-purple-500 to-pink-500 rounded-full text-xs font-bold text-white flex items-center justify-center">
              {cartCount}
            </span>
          )}
        </button>
      </div>

      {/* Trust badges */}
      <div className="grid grid-cols-3 gap-3 mb-8 slide-up" style={{ animationDelay: '0.05s' }}>
        {[
          { icon: Truck, label: 'Free shipping over $100' },
          { icon: ShieldCheck, label: 'Secure checkout' },
          { icon: Package, label: 'Easy returns' },
        ].map(({ icon: Icon, label }) => (
          <div key={label} className="glass px-3 py-2.5 flex items-center gap-2.5">
            <Icon className="w-4 h-4 text-purple-400 shrink-0" />
            <span className="text-white/50 text-xs">{label}</span>
          </div>
        ))}
      </div>

      {/* Search + Category filter */}
      <div className="glass p-4 mb-6 flex flex-col sm:flex-row gap-3 slide-up" style={{ animationDelay: '0.1s' }}>
        <input
          type="text"
          placeholder="Search products..."
          value={search}
          onChange={e => setSearch(e.target.value)}
          className="input-field flex-1"
        />
        <div className="flex gap-2">
          {CATEGORIES.map(cat => (
            <button
              key={cat}
              onClick={() => setCategory(cat)}
              className={`px-4 py-2 rounded-xl text-sm font-medium transition-all ${
                category === cat
                  ? 'bg-gradient-to-r from-purple-500 to-pink-500 text-white'
                  : 'glass text-white/50 hover:text-white'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Product grid */}
      <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-5">
        {filtered.map((p, i) => {
          const inCart = cart.find(c => c.product.id === p.id);
          return (
            <div key={p.id} className="glass card-hover overflow-hidden group slide-up" style={{ animationDelay: `${i * 0.05}s` }}>
              {/* Image */}
              <div className="relative h-52 overflow-hidden bg-gradient-to-br from-slate-800 to-slate-900">
                <img src={p.image} alt={p.name} className="w-full h-full object-cover opacity-80 group-hover:opacity-100 group-hover:scale-105 transition-all duration-500" />
                <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
                {p.badge && (
                  <span className={`absolute top-3 left-3 text-xs font-semibold px-2.5 py-1 rounded-full border ${p.badgeColor}`}>
                    {p.badge}
                  </span>
                )}
                <div className="absolute top-3 right-3 glass px-2 py-1 rounded-lg flex items-center gap-1">
                  <Tag className="w-3 h-3 text-purple-400" />
                  <span className="text-white text-xs font-bold">${p.price}</span>
                </div>
              </div>

              {/* Info */}
              <div className="p-4">
                <div className="text-white/40 text-xs mb-1">{p.category}</div>
                <h3 className="text-white font-bold text-sm mb-1 line-clamp-1 group-hover:gradient-text transition-all">{p.name}</h3>
                <p className="text-white/40 text-xs line-clamp-2 mb-3">{p.description}</p>

                {/* Rating */}
                <div className="flex items-center gap-1.5 mb-4">
                  <div className="flex">
                    {[...Array(5)].map((_, i) => (
                      <Star key={i} className={`w-3 h-3 ${i < Math.floor(p.rating) ? 'text-amber-400 fill-amber-400' : 'text-white/20'}`} />
                    ))}
                  </div>
                  <span className="text-white/40 text-xs">{p.rating} ({p.reviews})</span>
                </div>

                {/* Add to cart */}
                {inCart ? (
                  <div className="flex items-center justify-between glass px-3 py-2 rounded-xl">
                    <button onClick={() => updateQty(p.id, -1)} className="text-white/60 hover:text-white transition-colors">
                      <Minus className="w-4 h-4" />
                    </button>
                    <span className="text-white font-bold">{inCart.quantity}</span>
                    <button onClick={() => updateQty(p.id, 1)} className="text-white/60 hover:text-white transition-colors">
                      <Plus className="w-4 h-4" />
                    </button>
                  </div>
                ) : (
                  <button
                    onClick={() => { addToCart(p); }}
                    className="btn-primary w-full py-2.5 rounded-xl text-sm flex items-center justify-center gap-1.5"
                  >
                    <ShoppingCart className="w-4 h-4" /> Add to Cart
                  </button>
                )}
              </div>
            </div>
          );
        })}
      </div>

      {filtered.length === 0 && (
        <div className="text-center py-20 glass rounded-2xl mt-4">
          <Package className="w-16 h-16 text-white/10 mx-auto mb-4" />
          <h3 className="text-xl font-bold text-white mb-2">No products found</h3>
          <p className="text-white/30">Try a different search or category</p>
        </div>
      )}

      {/* Floating cart bar when items in cart */}
      {cartCount > 0 && !cartOpen && (
        <div className="fixed bottom-6 left-1/2 -translate-x-1/2 z-40 slide-up">
          <button
            onClick={() => setCartOpen(true)}
            className="bg-gradient-to-r from-purple-600 to-pink-600 text-white px-6 py-3.5 rounded-2xl flex items-center gap-3 shadow-2xl shadow-purple-500/30 hover:scale-105 transition-transform"
          >
            <ShoppingCart className="w-5 h-5" />
            <span className="font-bold">{cartCount} item{cartCount !== 1 ? 's' : ''} in cart</span>
            <span className="bg-white/20 px-2 py-0.5 rounded-lg text-sm font-black">
              ${cart.reduce((s, i) => s + i.product.price * i.quantity, 0).toFixed(2)}
            </span>
          </button>
        </div>
      )}

      {/* Cart sidebar */}
      {cartOpen && (
        <CartSidebar
          cart={cart}
          onClose={() => setCartOpen(false)}
          onQty={updateQty}
          onRemove={removeFromCart}
          onCheckout={() => { setCartOpen(false); setCheckoutOpen(true); }}
        />
      )}

      {/* Checkout modal */}
      {checkoutOpen && (
        <CheckoutModal
          cart={cart}
          onClose={() => setCheckoutOpen(false)}
          onSuccess={handleSuccess}
        />
      )}
    </div>
  );
}
