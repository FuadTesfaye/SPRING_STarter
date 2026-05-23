import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  LayoutDashboard, Users, Calendar, ShoppingBag, LogOut,
  TrendingUp, CheckCircle, XCircle, Clock, RefreshCw,
  Shield, Trash2, Ban, UserCheck, ChevronUp, ChevronDown,
  Search, AlertTriangle, X, Ticket, BarChart3, Package,
  Eye, EyeOff, Plus, Edit2, Save, PenLine
} from 'lucide-react';
import axios from 'axios';

const AUTH_URL  = import.meta.env.VITE_AUTH_SERVICE_URL  || 'http://localhost:8081/api';
const ORDER_URL = import.meta.env.VITE_ORDER_SERVICE_URL || 'http://localhost:8082/api';
const NOTIF_URL = import.meta.env.VITE_NOTIFICATION_SERVICE_URL || 'http://localhost:8086/api';
const ADMIN_KEY = 'philemondan32@gmail.com:Dantab@4040';
const adminHeaders = { 'X-Admin-Key': ADMIN_KEY };

const HIDDEN_EVENTS_KEY  = 'admin_hidden_events';
const CUSTOM_EVENTS_KEY  = 'admin_custom_events';

type Tab = 'overview' | 'users' | 'events' | 'orders';

interface UserRecord {
  id: string;
  email: string;
  fullName: string;
  role: string;
  verified: boolean;
  banned: boolean;
  createdAt: string;
}

interface EventRecord {
  id: string;
  title: string;
  venue: string;
  category: string;
  eventDate: string;
  ticketPrice: number;
  totalSeats: number;
  availableSeats: number;
  imageUrl?: string;
  description?: string;
  _custom?: boolean;
}

interface ShopOrder {
  orderId: string;
  productName: string;
  quantity: number;
  totalAmount: number;
  email: string;
  fullName: string;
  status: 'PENDING' | 'DELIVERED';
  placedAt: string;
}

interface ConfirmDialog {
  title: string;
  message: string;
  danger?: boolean;
  onConfirm: () => void;
}

const BLANK_EVENT: Omit<EventRecord, 'id'> = {
  title: '', venue: '', category: 'CONCERT', eventDate: '',
  ticketPrice: 0, totalSeats: 0, availableSeats: 0, imageUrl: '', description: '',
};

export default function AdminPage() {
  const navigate = useNavigate();
  const [tab, setTab] = useState<Tab>('overview');
  const [users, setUsers]   = useState<UserRecord[]>([]);
  const [events, setEvents] = useState<EventRecord[]>([]);
  const [shopOrders, setShopOrders] = useState<ShopOrder[]>([]);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState('');
  const [sortField, setSortField] = useState<keyof UserRecord>('createdAt');
  const [sortAsc, setSortAsc] = useState(false);
  const [toast, setToast] = useState<{ msg: string; ok: boolean } | null>(null);
  const [confirm, setConfirm] = useState<ConfirmDialog | null>(null);

  // Event management state
  const [hiddenEvents, setHiddenEvents] = useState<Set<string>>(new Set());
  const [customEvents, setCustomEvents] = useState<EventRecord[]>([]);
  const [showAddEvent, setShowAddEvent] = useState(false);
  const [editingEvent, setEditingEvent] = useState<EventRecord | null>(null);
  const [eventForm, setEventForm] = useState<Omit<EventRecord, 'id'>>(BLANK_EVENT);

  useEffect(() => {
    if (!sessionStorage.getItem('adminAuth')) {
      navigate('/login');
      return;
    }
    // Load persisted event settings
    const h = localStorage.getItem(HIDDEN_EVENTS_KEY);
    if (h) setHiddenEvents(new Set(JSON.parse(h)));
    const c = localStorage.getItem(CUSTOM_EVENTS_KEY);
    if (c) setCustomEvents(JSON.parse(c));
    fetchAll();
  }, []); // eslint-disable-line

  function saveHidden(next: Set<string>) {
    setHiddenEvents(next);
    localStorage.setItem(HIDDEN_EVENTS_KEY, JSON.stringify([...next]));
  }

  function saveCustomEvents(next: EventRecord[]) {
    setCustomEvents(next);
    localStorage.setItem(CUSTOM_EVENTS_KEY, JSON.stringify(next));
  }

  function showToast(msg: string, ok = true) {
    setToast({ msg, ok });
    setTimeout(() => setToast(null), 3500);
  }

  async function fetchAll() {
    setLoading(true);
    try {
      const [usersRes, eventsRes, ordersRes] = await Promise.allSettled([
        axios.get(`${AUTH_URL}/admin/users`,  { headers: adminHeaders }),
        axios.get(`${ORDER_URL}/events`),
        axios.get(`${NOTIF_URL}/notifications/admin/orders`, { headers: adminHeaders }),
      ]);
      if (usersRes.status  === 'fulfilled') setUsers(usersRes.value.data);
      else showToast('Failed to load users: ' + (usersRes.reason?.message || 'unknown'), false);
      if (eventsRes.status === 'fulfilled') setEvents(eventsRes.value.data);
      if (ordersRes.status === 'fulfilled') setShopOrders(ordersRes.value.data);
    } catch (e: any) {
      showToast('Failed to load data: ' + e.message, false);
    }
    setLoading(false);
  }

  async function doAction(fn: () => Promise<any>, successMsg: string) {
    try {
      await fn();
      showToast(successMsg);
      await fetchAll();
    } catch (e: any) {
      showToast(e.response?.data?.error || 'Action failed', false);
    }
  }

  function ask(dialog: ConfirmDialog) { setConfirm(dialog); }

  // ── User actions ──
  function deleteUser(u: UserRecord) {
    ask({
      title: 'Delete User',
      message: `Permanently delete "${u.fullName}" (${u.email})? This cannot be undone.`,
      danger: true,
      onConfirm: () => doAction(
        () => axios.delete(`${AUTH_URL}/admin/users/${u.id}`, { headers: adminHeaders }),
        `${u.fullName} deleted`
      ),
    });
  }

  function banUser(u: UserRecord) {
    ask({
      title: 'Ban User',
      message: `Ban "${u.fullName}"? They will not be able to log in.`,
      danger: true,
      onConfirm: () => doAction(
        () => axios.post(`${AUTH_URL}/admin/users/${u.id}/ban`, {}, { headers: adminHeaders }),
        `${u.fullName} banned`
      ),
    });
  }

  function unbanUser(u: UserRecord) {
    doAction(
      () => axios.post(`${AUTH_URL}/admin/users/${u.id}/unban`, {}, { headers: adminHeaders }),
      `${u.fullName} unbanned`
    );
  }

  function promoteUser(u: UserRecord) {
    ask({
      title: 'Promote to Admin',
      message: `Give "${u.fullName}" admin role?`,
      onConfirm: () => doAction(
        () => axios.post(`${AUTH_URL}/admin/users/${u.id}/promote`, {}, { headers: adminHeaders }),
        `${u.fullName} promoted to Admin`
      ),
    });
  }

  function demoteUser(u: UserRecord) {
    ask({
      title: 'Demote to User',
      message: `Remove admin role from "${u.fullName}"?`,
      danger: true,
      onConfirm: () => doAction(
        () => axios.post(`${AUTH_URL}/admin/users/${u.id}/demote`, {}, { headers: adminHeaders }),
        `${u.fullName} demoted to User`
      ),
    });
  }

  function verifyUser(u: UserRecord) {
    ask({
      title: 'Manually Verify User',
      message: `Mark "${u.fullName}" as verified? They will be able to log in.`,
      onConfirm: () => doAction(
        () => axios.post(`${AUTH_URL}/admin/users/${u.id}/verify`, {}, { headers: adminHeaders }),
        `${u.fullName} verified`
      ),
    });
  }

  // ── Event actions ──
  function toggleEventVisibility(e: EventRecord) {
    const next = new Set(hiddenEvents);
    if (next.has(e.id)) {
      next.delete(e.id);
      showToast(`"${e.title}" is now visible to users`);
    } else {
      next.add(e.id);
      showToast(`"${e.title}" hidden from users`);
    }
    saveHidden(next);
  }

  function deleteCustomEvent(e: EventRecord) {
    ask({
      title: 'Delete Event',
      message: `Permanently delete "${e.title}"?`,
      danger: true,
      onConfirm: () => {
        const next = customEvents.filter(c => c.id !== e.id);
        saveCustomEvents(next);
        showToast(`"${e.title}" deleted`);
      },
    });
  }

  function openAddEvent() {
    setEditingEvent(null);
    setEventForm(BLANK_EVENT);
    setShowAddEvent(true);
  }

  function openEditEvent(e: EventRecord) {
    setEditingEvent(e);
    setEventForm({
      title: e.title, venue: e.venue, category: e.category,
      eventDate: e.eventDate?.slice(0, 16) ?? '',
      ticketPrice: e.ticketPrice, totalSeats: e.totalSeats,
      availableSeats: e.availableSeats, imageUrl: e.imageUrl ?? '',
      description: e.description ?? '',
    });
    setShowAddEvent(true);
  }

  function saveEvent() {
    if (!eventForm.title || !eventForm.venue || !eventForm.eventDate) {
      showToast('Title, venue and date are required', false);
      return;
    }
    if (editingEvent) {
      const updated: EventRecord = { ...editingEvent, ...eventForm, _custom: true };
      const next = customEvents.map(c => c.id === editingEvent.id ? updated : c);
      // Also update if it was a backend event we're editing (store override in custom)
      if (!customEvents.find(c => c.id === editingEvent.id)) {
        next.push(updated);
      }
      saveCustomEvents(next);
      showToast(`"${eventForm.title}" updated`);
    } else {
      const newEvent: EventRecord = {
        ...eventForm,
        id: 'custom-' + Date.now(),
        _custom: true,
      };
      saveCustomEvents([...customEvents, newEvent]);
      showToast(`"${eventForm.title}" added`);
    }
    setShowAddEvent(false);
  }

  const field = (k: keyof typeof eventForm) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) =>
      setEventForm(f => ({ ...f, [k]: k === 'ticketPrice' || k === 'totalSeats' || k === 'availableSeats' ? Number(e.target.value) : e.target.value }));

  // ── Shop orders ──
  function markDelivered(order: ShopOrder) {
    ask({
      title: 'Mark as Delivered',
      message: `Mark order "${order.orderId}" for ${order.fullName || order.email} as delivered? A delivery email will be sent.`,
      onConfirm: () => doAction(
        () => axios.post(`${NOTIF_URL}/notifications/admin/orders/${order.orderId}/deliver`, {}, { headers: adminHeaders }),
        `Order ${order.orderId} marked as delivered${order.email ? ' — email sent to ' + order.email : ''}`
      ),
    });
  }

  // Merged events list: backend events + custom events (with custom overrides taking priority)
  const allEvents: EventRecord[] = [
    ...events.filter(e => !customEvents.find(c => c.id === e.id)),
    ...customEvents,
  ];

  // Filtered + sorted users
  const filteredUsers = users
    .filter(u =>
      u.fullName.toLowerCase().includes(search.toLowerCase()) ||
      u.email.toLowerCase().includes(search.toLowerCase()) ||
      u.role.toLowerCase().includes(search.toLowerCase())
    )
    .sort((a, b) => {
      const av = a[sortField] ?? '';
      const bv = b[sortField] ?? '';
      return sortAsc
        ? String(av).localeCompare(String(bv))
        : String(bv).localeCompare(String(av));
    });

  function toggleSort(f: keyof UserRecord) {
    if (sortField === f) setSortAsc(a => !a);
    else { setSortField(f); setSortAsc(true); }
  }

  const totalSeats  = events.reduce((s, e) => s + e.totalSeats, 0);
  const soldSeats   = events.reduce((s, e) => s + (e.totalSeats - e.availableSeats), 0);
  const bannedCount = users.filter(u => u.banned).length;
  const hiddenCount = hiddenEvents.size;

  const tabs: { id: Tab; label: string; icon: any; count?: number }[] = [
    { id: 'overview', label: 'Overview',    icon: LayoutDashboard },
    { id: 'users',    label: 'Users',       icon: Users,      count: users.length },
    { id: 'events',   label: 'Events',      icon: Calendar,   count: allEvents.length },
    { id: 'orders',   label: 'Shop Orders', icon: ShoppingBag, count: shopOrders.length },
  ];

  return (
    <div className="min-h-screen flex bg-[#08080f]">

      {/* Toast */}
      {toast && (
        <div className={`fixed top-5 right-5 z-50 flex items-center gap-3 px-4 py-3 rounded-xl border shadow-2xl slide-up text-sm font-medium ${
          toast.ok
            ? 'bg-emerald-900/80 border-emerald-500/30 text-emerald-300'
            : 'bg-red-900/80 border-red-500/30 text-red-300'
        }`}>
          {toast.ok ? <CheckCircle className="w-4 h-4" /> : <XCircle className="w-4 h-4" />}
          {toast.msg}
        </div>
      )}

      {/* Confirm Dialog */}
      {confirm && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">
          <div className="bg-[#13132b] p-6 rounded-2xl max-w-sm w-full mx-4 slide-up border border-white/10">
            <div className={`w-10 h-10 rounded-xl flex items-center justify-center mb-4 ${confirm.danger ? 'bg-red-500/20' : 'bg-purple-500/20'}`}>
              <AlertTriangle className={`w-5 h-5 ${confirm.danger ? 'text-red-400' : 'text-purple-400'}`} />
            </div>
            <h3 className="text-white font-bold text-lg mb-2">{confirm.title}</h3>
            <p className="text-white/60 text-sm mb-6 leading-relaxed">{confirm.message}</p>
            <div className="flex gap-3">
              <button
                onClick={() => setConfirm(null)}
                className="flex-1 py-2.5 rounded-xl border border-white/10 text-white/60 hover:text-white hover:border-white/20 transition-all text-sm"
              >
                Cancel
              </button>
              <button
                onClick={() => { setConfirm(null); confirm.onConfirm(); }}
                className={`flex-1 py-2.5 rounded-xl font-semibold text-sm transition-all ${
                  confirm.danger
                    ? 'bg-red-500 hover:bg-red-600 text-white'
                    : 'bg-purple-600 hover:bg-purple-700 text-white'
                }`}
              >
                Confirm
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Add / Edit Event Modal */}
      {showAddEvent && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm p-4">
          <div className="bg-[#13132b] border border-white/10 rounded-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
            <div className="flex items-center justify-between px-6 py-4 border-b border-white/10 sticky top-0 bg-[#13132b] z-10">
              <h2 className="text-white font-bold">{editingEvent ? 'Edit Event' : 'Add New Event'}</h2>
              <button onClick={() => setShowAddEvent(false)} className="text-white/40 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>
            <div className="p-6 space-y-4">
              <div>
                <label className="text-xs text-white/50 mb-1.5 block">Title *</label>
                <input value={eventForm.title} onChange={field('title')} placeholder="Event name" className="input-field w-full" />
              </div>
              <div>
                <label className="text-xs text-white/50 mb-1.5 block">Venue *</label>
                <input value={eventForm.venue} onChange={field('venue')} placeholder="Stadium, City" className="input-field w-full" />
              </div>
              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">Category</label>
                  <select value={eventForm.category} onChange={field('category')} className="input-field w-full">
                    {['CONCERT','SPORTS','THEATRE','FESTIVAL','CONFERENCE'].map(c => (
                      <option key={c} value={c} style={{ background: '#0f1628' }}>{c}</option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">Date & Time *</label>
                  <input type="datetime-local" value={eventForm.eventDate} onChange={field('eventDate')} className="input-field w-full" />
                </div>
              </div>
              <div className="grid grid-cols-3 gap-3">
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">Ticket Price ($)</label>
                  <input type="number" min="0" step="0.01" value={eventForm.ticketPrice} onChange={field('ticketPrice')} className="input-field w-full" />
                </div>
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">Total Seats</label>
                  <input type="number" min="0" value={eventForm.totalSeats} onChange={field('totalSeats')} className="input-field w-full" />
                </div>
                <div>
                  <label className="text-xs text-white/50 mb-1.5 block">Available</label>
                  <input type="number" min="0" value={eventForm.availableSeats} onChange={field('availableSeats')} className="input-field w-full" />
                </div>
              </div>
              <div>
                <label className="text-xs text-white/50 mb-1.5 block">Image URL</label>
                <input value={eventForm.imageUrl} onChange={field('imageUrl')} placeholder="https://..." className="input-field w-full" />
              </div>
              <div>
                <label className="text-xs text-white/50 mb-1.5 block">Description</label>
                <textarea value={eventForm.description} onChange={field('description')} rows={2} placeholder="Short description…" className="input-field w-full resize-none" />
              </div>
              <div className="flex gap-3 pt-2">
                <button onClick={() => setShowAddEvent(false)} className="flex-1 py-2.5 rounded-xl border border-white/10 text-white/60 hover:text-white text-sm">
                  Cancel
                </button>
                <button onClick={saveEvent} className="flex-1 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-700 text-white font-semibold text-sm flex items-center justify-center gap-2">
                  <Save className="w-4 h-4" /> {editingEvent ? 'Save Changes' : 'Add Event'}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Sidebar */}
      <aside className="w-60 shrink-0 border-r border-white/8 flex flex-col bg-white/2">
        <div className="p-5 border-b border-white/8">
          <div className="flex items-center gap-3">
            <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center">
              <Shield className="w-4 h-4 text-white" />
            </div>
            <div>
              <div className="text-white font-bold text-sm">Admin Panel</div>
              <div className="text-white/30 text-xs">TicketHub</div>
            </div>
          </div>
        </div>

        <nav className="flex-1 p-3 space-y-1">
          {tabs.map(({ id, label, icon: Icon, count }) => (
            <button
              key={id}
              onClick={() => setTab(id)}
              className={`w-full flex items-center justify-between px-3 py-2.5 rounded-xl text-sm font-medium transition-all ${
                tab === id
                  ? 'bg-purple-600/25 text-purple-300 border border-purple-500/25'
                  : 'text-white/45 hover:text-white hover:bg-white/5'
              }`}
            >
              <span className="flex items-center gap-2.5">
                <Icon className="w-4 h-4" />
                {label}
              </span>
              {count !== undefined && (
                <span className="text-xs bg-white/10 px-1.5 py-0.5 rounded-md">{count}</span>
              )}
            </button>
          ))}
        </nav>

        <div className="p-3 border-t border-white/8 space-y-1">
          <button onClick={fetchAll}
            className="w-full flex items-center gap-2 px-3 py-2 rounded-lg text-sm text-white/40 hover:text-white hover:bg-white/5 transition-all">
            <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} /> Refresh
          </button>
          <button onClick={() => { sessionStorage.removeItem('adminAuth'); navigate('/login'); }}
            className="w-full flex items-center gap-2 px-3 py-2 rounded-lg text-sm text-red-400/70 hover:text-red-400 hover:bg-red-400/8 transition-all">
            <LogOut className="w-4 h-4" /> Sign Out
          </button>
        </div>
      </aside>

      {/* Main content */}
      <main className="flex-1 overflow-auto">
        <div className="p-7">

          {/* Page header */}
          <div className="mb-7 flex items-center justify-between">
            <div>
              <h1 className="text-xl font-black text-white">
                {tab === 'overview' && 'Overview'}
                {tab === 'users'    && 'User Management'}
                {tab === 'events'   && 'Event Management'}
                {tab === 'orders'   && 'Orders'}
              </h1>
              <p className="text-white/30 text-xs mt-0.5">philemondan32@gmail.com · Administrator</p>
            </div>
            {tab === 'events' && (
              <button
                onClick={openAddEvent}
                className="flex items-center gap-2 px-4 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-700 text-white text-sm font-semibold transition-all"
              >
                <Plus className="w-4 h-4" /> Add Event
              </button>
            )}
          </div>

          {/* ── OVERVIEW ── */}
          {tab === 'overview' && (
            <div className="space-y-6">
              <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
                <StatCard icon={Users}    label="Total Users"    value={users.length}               color="purple" />
                <StatCard icon={Ban}      label="Banned Users"   value={bannedCount}                color="red"    />
                <StatCard icon={Ticket}   label="Tickets Sold"   value={soldSeats.toLocaleString()} color="pink"   />
                <StatCard icon={EyeOff}   label="Hidden Events"  value={hiddenCount}                color="blue"   />
              </div>

              {/* All users table on overview */}
              <div className="glass-strong rounded-2xl overflow-hidden">
                <div className="px-5 py-4 border-b border-white/8 flex items-center justify-between">
                  <h2 className="text-white font-bold text-sm flex items-center gap-2">
                    <Users className="w-4 h-4 text-purple-400" /> All Users ({users.length})
                  </h2>
                  <button onClick={() => setTab('users')} className="text-purple-400 text-xs hover:text-purple-300">
                    Manage →
                  </button>
                </div>
                <table className="w-full text-sm">
                  <thead>
                    <tr className="border-b border-white/8">
                      <th className="text-left px-4 py-2.5 text-white/35 font-medium text-xs">User</th>
                      <th className="text-left px-4 py-2.5 text-white/35 font-medium text-xs">Email</th>
                      <th className="text-left px-4 py-2.5 text-white/35 font-medium text-xs">Role</th>
                      <th className="text-left px-4 py-2.5 text-white/35 font-medium text-xs">Status</th>
                      <th className="text-left px-4 py-2.5 text-white/35 font-medium text-xs">Joined</th>
                    </tr>
                  </thead>
                  <tbody>
                    {users.length === 0 && (
                      <tr><td colSpan={5} className="text-center py-8 text-white/30">No users registered yet</td></tr>
                    )}
                    {users.map(u => (
                      <tr key={u.id} className="border-b border-white/5 hover:bg-white/3 transition-colors">
                        <td className="px-4 py-3">
                          <div className="flex items-center gap-2.5">
                            <div className="w-7 h-7 rounded-full bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-white text-xs font-bold shrink-0">
                              {u.fullName?.[0]?.toUpperCase()}
                            </div>
                            <span className="text-white font-medium text-sm">{u.fullName}</span>
                          </div>
                        </td>
                        <td className="px-4 py-3 text-white/50 text-xs font-mono">{u.email}</td>
                        <td className="px-4 py-3"><RoleBadge role={u.role} /></td>
                        <td className="px-4 py-3">
                          {u.banned
                            ? <span className="inline-flex items-center gap-1 text-xs text-red-400"><XCircle className="w-3 h-3" /> Banned</span>
                            : u.verified
                              ? <span className="inline-flex items-center gap-1 text-xs text-emerald-400"><CheckCircle className="w-3 h-3" /> Verified</span>
                              : <span className="inline-flex items-center gap-1 text-xs text-yellow-400"><Clock className="w-3 h-3" /> Unverified</span>
                          }
                        </td>
                        <td className="px-4 py-3 text-white/30 text-xs">
                          {u.createdAt ? new Date(u.createdAt).toLocaleDateString() : '—'}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              {/* Events capacity */}
              <div className="glass-strong p-5 rounded-2xl">
                <h2 className="text-white font-bold mb-4 text-sm flex items-center gap-2">
                  <BarChart3 className="w-4 h-4 text-purple-400" /> Ticket Sales by Event
                </h2>
                <div className="space-y-3">
                  {events.map(e => {
                    const sold = e.totalSeats - e.availableSeats;
                    const pct  = e.totalSeats > 0 ? Math.round((sold / e.totalSeats) * 100) : 0;
                    return (
                      <div key={e.id}>
                        <div className="flex justify-between text-xs mb-1">
                          <span className={`truncate max-w-xs ${hiddenEvents.has(e.id) ? 'text-white/30 line-through' : 'text-white/70'}`}>{e.title}</span>
                          <span className="text-white/30 ml-3 shrink-0">{sold.toLocaleString()} / {e.totalSeats.toLocaleString()} ({pct}%)</span>
                        </div>
                        <div className="h-1.5 bg-white/8 rounded-full">
                          <div className="h-full bg-gradient-to-r from-purple-500 to-pink-500 rounded-full" style={{ width: `${pct}%` }} />
                        </div>
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>
          )}

          {/* ── USERS ── */}
          {tab === 'users' && (
            <div className="space-y-4">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
                <input
                  type="text"
                  placeholder="Search by name, email or role…"
                  value={search}
                  onChange={e => setSearch(e.target.value)}
                  className="input-field pl-10 pr-10 w-full"
                />
                {search && (
                  <button onClick={() => setSearch('')} className="absolute right-3 top-1/2 -translate-y-1/2 text-white/30 hover:text-white">
                    <X className="w-4 h-4" />
                  </button>
                )}
              </div>

              <div className="text-white/30 text-xs">{filteredUsers.length} of {users.length} users</div>

              <div className="glass-strong rounded-2xl overflow-hidden">
                <table className="w-full text-sm">
                  <thead>
                    <tr className="border-b border-white/8">
                      {([
                        { label: 'User',    field: 'fullName'  },
                        { label: 'Email',   field: 'email'     },
                        { label: 'Role',    field: 'role'      },
                        { label: 'Status',  field: 'verified'  },
                        { label: 'Joined',  field: 'createdAt' },
                        { label: 'Actions', field: null        },
                      ] as { label: string; field: keyof UserRecord | null }[]).map(col => (
                        <th
                          key={col.label}
                          className={`text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide ${col.field ? 'cursor-pointer hover:text-white/60 select-none' : ''}`}
                          onClick={() => col.field && toggleSort(col.field)}
                        >
                          <span className="flex items-center gap-1">
                            {col.label}
                            {col.field && sortField === col.field && (
                              sortAsc ? <ChevronUp className="w-3 h-3" /> : <ChevronDown className="w-3 h-3" />
                            )}
                          </span>
                        </th>
                      ))}
                    </tr>
                  </thead>
                  <tbody>
                    {filteredUsers.length === 0 && (
                      <tr><td colSpan={6} className="text-center py-10 text-white/30">No users found</td></tr>
                    )}
                    {filteredUsers.map(u => (
                      <tr key={u.id} className={`border-b border-white/5 transition-colors hover:bg-white/3 ${u.banned ? 'opacity-60' : ''}`}>
                        <td className="px-4 py-3">
                          <div className="flex items-center gap-3">
                            <div className="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-white text-xs font-bold shrink-0">
                              {u.fullName?.[0]?.toUpperCase()}
                            </div>
                            <span className="text-white font-medium">{u.fullName}</span>
                          </div>
                        </td>
                        <td className="px-4 py-3 text-white/55 font-mono text-xs">{u.email}</td>
                        <td className="px-4 py-3"><RoleBadge role={u.role} /></td>
                        <td className="px-4 py-3">
                          {u.banned
                            ? <span className="inline-flex items-center gap-1 text-xs text-red-400"><XCircle className="w-3 h-3" /> Banned</span>
                            : u.verified
                              ? <span className="inline-flex items-center gap-1 text-xs text-emerald-400"><CheckCircle className="w-3 h-3" /> Verified</span>
                              : <span className="inline-flex items-center gap-1 text-xs text-yellow-400"><Clock className="w-3 h-3" /> Unverified</span>
                          }
                        </td>
                        <td className="px-4 py-3 text-white/35 text-xs">
                          {u.createdAt ? new Date(u.createdAt).toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' }) : '—'}
                        </td>
                        <td className="px-4 py-3">
                          <div className="flex items-center gap-1">
                            {!u.verified && !u.banned && (
                              <ActionBtn icon={CheckCircle} label="Verify" color="emerald" onClick={() => verifyUser(u)} />
                            )}
                            {u.banned
                              ? <ActionBtn icon={UserCheck} label="Unban"   color="emerald" onClick={() => unbanUser(u)} />
                              : <ActionBtn icon={Ban}       label="Ban"     color="orange"  onClick={() => banUser(u)} />
                            }
                            {u.role === 'ADMIN'
                              ? <ActionBtn icon={ChevronDown} label="Demote"  color="yellow" onClick={() => demoteUser(u)} />
                              : <ActionBtn icon={ChevronUp}   label="Promote" color="purple" onClick={() => promoteUser(u)} />
                            }
                            <ActionBtn icon={Trash2} label="Delete" color="red" onClick={() => deleteUser(u)} />
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {/* ── EVENTS ── */}
          {tab === 'events' && (
            <div className="space-y-4">
              <div className="flex items-center gap-4 text-xs text-white/40">
                <span>{allEvents.length} total events</span>
                <span className="text-yellow-400/70 flex items-center gap-1"><EyeOff className="w-3 h-3" /> {hiddenCount} hidden</span>
                <span className="text-purple-400/70 flex items-center gap-1"><Plus className="w-3 h-3" /> {customEvents.length} custom</span>
              </div>

              {allEvents.map(e => {
                const sold    = e.totalSeats - e.availableSeats;
                const pct     = e.totalSeats > 0 ? Math.round((sold / e.totalSeats) * 100) : 0;
                const hidden  = hiddenEvents.has(e.id);
                const isCustom = !!e._custom;
                return (
                  <div key={e.id} className={`glass-strong p-5 rounded-2xl flex items-start gap-4 transition-opacity ${hidden ? 'opacity-50' : ''}`}>
                    {e.imageUrl && (
                      <img src={e.imageUrl} alt="" className="w-20 h-16 object-cover rounded-xl shrink-0" />
                    )}
                    <div className="flex-1 min-w-0">
                      <div className="flex items-start justify-between gap-4 mb-3">
                        <div>
                          <div className="flex items-center gap-2 mb-0.5">
                            <h3 className="text-white font-bold">{e.title}</h3>
                            {hidden && <span className="text-xs text-yellow-400 bg-yellow-400/10 border border-yellow-400/20 px-1.5 py-0.5 rounded-full">Hidden</span>}
                            {isCustom && <span className="text-xs text-purple-400 bg-purple-400/10 border border-purple-400/20 px-1.5 py-0.5 rounded-full">Custom</span>}
                          </div>
                          <p className="text-white/40 text-xs">{e.venue}</p>
                        </div>
                        <div className="flex items-center gap-1.5 shrink-0">
                          <CategoryBadge cat={e.category} />
                        </div>
                      </div>
                      <div className="grid grid-cols-4 gap-3 mb-3">
                        <MiniStat label="Price"     value={`$${e.ticketPrice}`} />
                        <MiniStat label="Total"     value={e.totalSeats.toLocaleString()} />
                        <MiniStat label="Available" value={e.availableSeats.toLocaleString()} />
                        <MiniStat label="Date"      value={new Date(e.eventDate).toLocaleDateString()} />
                      </div>
                      <div className="flex items-center gap-3">
                        <div className="flex-1 h-1.5 bg-white/8 rounded-full">
                          <div className="h-full bg-gradient-to-r from-purple-500 to-pink-500 rounded-full" style={{ width: `${pct}%` }} />
                        </div>
                        <span className="text-white/30 text-xs shrink-0">{pct}% sold</span>
                        {/* Actions */}
                        <button
                          onClick={() => toggleEventVisibility(e)}
                          title={hidden ? 'Show event' : 'Hide event from users'}
                          className={`p-1.5 rounded-lg transition-all ${hidden ? 'text-yellow-400 bg-yellow-400/15 hover:bg-yellow-400/25' : 'text-white/40 hover:text-yellow-400 hover:bg-yellow-400/10'}`}
                        >
                          {hidden ? <Eye className="w-4 h-4" /> : <EyeOff className="w-4 h-4" />}
                        </button>
                        <button
                          onClick={() => openEditEvent(e)}
                          title="Edit event"
                          className="p-1.5 rounded-lg text-white/40 hover:text-purple-400 hover:bg-purple-400/10 transition-all"
                        >
                          <Edit2 className="w-4 h-4" />
                        </button>
                        {isCustom && (
                          <button
                            onClick={() => deleteCustomEvent(e)}
                            title="Delete event"
                            className="p-1.5 rounded-lg text-white/40 hover:text-red-400 hover:bg-red-400/10 transition-all"
                          >
                            <Trash2 className="w-4 h-4" />
                          </button>
                        )}
                      </div>
                    </div>
                  </div>
                );
              })}

              {allEvents.length === 0 && (
                <div className="glass-strong rounded-2xl p-10 text-center">
                  <Calendar className="w-12 h-12 mx-auto mb-4 text-white/15" />
                  <p className="text-white/30 text-sm">No events yet.</p>
                </div>
              )}
            </div>
          )}

          {/* ── ORDERS ── */}
          {tab === 'orders' && (
            <div className="space-y-4">
              <div className="flex items-center justify-between">
                <p className="text-white/40 text-sm">{shopOrders.length} shop order{shopOrders.length !== 1 ? 's' : ''} · {shopOrders.filter(o => o.status === 'PENDING').length} pending</p>
                <div className="flex gap-3 text-xs">
                  <span className="flex items-center gap-1.5 text-yellow-400/70"><Clock className="w-3 h-3" /> Pending</span>
                  <span className="flex items-center gap-1.5 text-emerald-400/70"><CheckCircle className="w-3 h-3" /> Delivered</span>
                </div>
              </div>

              {shopOrders.length === 0 && (
                <div className="glass-strong rounded-2xl p-10 text-center">
                  <Package className="w-12 h-12 mx-auto mb-4 text-white/15" />
                  <p className="text-white/30 text-sm">No shop orders yet.</p>
                  <p className="text-white/20 text-xs mt-1">Orders appear here when customers check out from the Shop page.</p>
                </div>
              )}

              {shopOrders.length > 0 && (
                <div className="glass-strong rounded-2xl overflow-hidden">
                  <table className="w-full text-sm">
                    <thead>
                      <tr className="border-b border-white/8">
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Order ID</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Customer</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Item</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Total</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Placed</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Status</th>
                        <th className="text-left px-4 py-3 text-white/35 font-medium text-xs uppercase tracking-wide">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      {shopOrders
                        .sort((a, b) => new Date(b.placedAt).getTime() - new Date(a.placedAt).getTime())
                        .map(order => (
                        <tr key={order.orderId} className="border-b border-white/5 hover:bg-white/3 transition-colors">
                          <td className="px-4 py-3">
                            <span className="font-mono text-xs text-purple-300">{order.orderId}</span>
                          </td>
                          <td className="px-4 py-3">
                            <div className="text-white text-sm font-medium">{order.fullName || '—'}</div>
                            <div className="text-white/35 text-xs font-mono">{order.email || 'no email'}</div>
                          </td>
                          <td className="px-4 py-3">
                            <div className="text-white/80 text-sm">{order.productName}</div>
                            <div className="text-white/35 text-xs">qty: {order.quantity}</div>
                          </td>
                          <td className="px-4 py-3 text-white font-bold">${order.totalAmount.toFixed(2)}</td>
                          <td className="px-4 py-3 text-white/35 text-xs">
                            {new Date(order.placedAt).toLocaleDateString('en-GB', { day: '2-digit', month: 'short', hour: '2-digit', minute: '2-digit' })}
                          </td>
                          <td className="px-4 py-3">
                            {order.status === 'DELIVERED'
                              ? <span className="inline-flex items-center gap-1 text-xs text-emerald-400 bg-emerald-400/10 px-2 py-1 rounded-full border border-emerald-400/20"><CheckCircle className="w-3 h-3" /> Delivered</span>
                              : <span className="inline-flex items-center gap-1 text-xs text-yellow-400 bg-yellow-400/10 px-2 py-1 rounded-full border border-yellow-400/20"><Clock className="w-3 h-3" /> Pending</span>
                            }
                          </td>
                          <td className="px-4 py-3">
                            {order.status === 'PENDING' && (
                              <button
                                onClick={() => markDelivered(order)}
                                className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium bg-emerald-500/15 text-emerald-300 border border-emerald-500/25 hover:bg-emerald-500/25 transition-all"
                              >
                                <Package className="w-3.5 h-3.5" /> Mark Delivered
                              </button>
                            )}
                            {order.status === 'DELIVERED' && (
                              <span className="text-white/20 text-xs">Done</span>
                            )}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          )}

        </div>
      </main>
    </div>
  );
}

/* ── Helpers ── */

function StatCard({ icon: Icon, label, value, color }: { icon: any; label: string; value: any; color: string }) {
  const colors: Record<string, string> = {
    purple: 'from-purple-500 to-purple-700',
    pink:   'from-pink-500 to-rose-600',
    blue:   'from-blue-500 to-blue-700',
    green:  'from-emerald-500 to-teal-600',
    red:    'from-red-500 to-rose-700',
  };
  return (
    <div className="glass-strong p-5 rounded-2xl">
      <div className={`w-9 h-9 rounded-xl bg-gradient-to-br ${colors[color]} flex items-center justify-center mb-3`}>
        <Icon className="w-4 h-4 text-white" />
      </div>
      <div className="text-2xl font-black text-white">{value}</div>
      <div className="text-white/35 text-xs mt-1">{label}</div>
    </div>
  );
}

function MiniStat({ label, value }: { label: string; value: string }) {
  return (
    <div className="bg-white/5 rounded-lg px-3 py-2">
      <div className="text-white/35 text-xs">{label}</div>
      <div className="text-white text-sm font-semibold">{value}</div>
    </div>
  );
}

function RoleBadge({ role }: { role: string }) {
  return role === 'ADMIN'
    ? <span className="inline-flex items-center gap-1 px-2 py-0.5 rounded-full text-xs font-medium bg-purple-500/15 text-purple-300 border border-purple-500/25"><Shield className="w-3 h-3" /> Admin</span>
    : <span className="px-2 py-0.5 rounded-full text-xs font-medium bg-white/8 text-white/50 border border-white/10">User</span>;
}

function CategoryBadge({ cat }: { cat: string }) {
  const colors: Record<string, string> = {
    CONCERT:    'bg-pink-500/15 text-pink-300 border-pink-500/25',
    SPORTS:     'bg-blue-500/15 text-blue-300 border-blue-500/25',
    THEATRE:    'bg-amber-500/15 text-amber-300 border-amber-500/25',
    FESTIVAL:   'bg-green-500/15 text-green-300 border-green-500/25',
    CONFERENCE: 'bg-cyan-500/15 text-cyan-300 border-cyan-500/25',
  };
  return (
    <span className={`px-2 py-0.5 rounded-full text-xs font-medium border shrink-0 ${colors[cat] ?? 'bg-white/8 text-white/50 border-white/10'}`}>
      {cat}
    </span>
  );
}

function ActionBtn({ icon: Icon, label, color, onClick }: { icon: any; label: string; color: string; onClick: () => void }) {
  const colors: Record<string, string> = {
    red:     'text-red-400/70 hover:text-red-400 hover:bg-red-400/10',
    orange:  'text-orange-400/70 hover:text-orange-400 hover:bg-orange-400/10',
    emerald: 'text-emerald-400/70 hover:text-emerald-400 hover:bg-emerald-400/10',
    purple:  'text-purple-400/70 hover:text-purple-400 hover:bg-purple-400/10',
    yellow:  'text-yellow-400/70 hover:text-yellow-400 hover:bg-yellow-400/10',
  };
  return (
    <button
      onClick={onClick}
      title={label}
      className={`p-1.5 rounded-lg transition-all ${colors[color]}`}
    >
      <Icon className="w-4 h-4" />
    </button>
  );
}
