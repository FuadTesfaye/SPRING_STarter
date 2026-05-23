import { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Ticket, Home, Calendar, LayoutDashboard, BookOpen, LogOut, LogIn, UserPlus, Menu, X, ShoppingBag, Bell } from 'lucide-react';
import { useAuthStore } from '../store';
import axios from 'axios';


const NOTIF_URL = import.meta.env.VITE_NOTIFICATION_SERVICE_URL || 'http://localhost:8086/api';

interface InAppNotif {
  id: string;
  title: string;
  message: string;
  read: boolean;
  createdAt: string;
}

export default function Header() {
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();
  const location = useLocation();
  const [mobileOpen, setMobileOpen] = useState(false);
  const [notifs, setNotifs] = useState<InAppNotif[]>([]);
  const [notifOpen, setNotifOpen] = useState(false);
  const [accountOpen, setAccountOpen] = useState(false);
  const notifRef = useRef<HTMLDivElement>(null);
  const accountRef = useRef<HTMLDivElement>(null);

  const unread = notifs.filter(n => !n.read).length;

  useEffect(() => {
    if (!user?.email) return;
    const fetch = () =>
      axios.get(`${NOTIF_URL}/notifications/inbox`, { params: { email: user.email } })
        .then(r => setNotifs(r.data))
        .catch(() => {});
    fetch();
    const id = setInterval(fetch, 15000);
    return () => clearInterval(id);
  }, [user?.email]);

  useEffect(() => {
    const handler = (e: MouseEvent) => {
      if (notifRef.current && !notifRef.current.contains(e.target as Node)) {
        setNotifOpen(false);
      }
      if (accountRef.current && !accountRef.current.contains(e.target as Node)) {
        setAccountOpen(false);
      }
    };
    document.addEventListener('mousedown', handler);
    return () => document.removeEventListener('mousedown', handler);
  }, []);

  const openNotifs = () => {
    setNotifOpen(o => !o);
    if (!notifOpen && user?.email) {
      axios.post(`${NOTIF_URL}/notifications/inbox/mark-read`, null, { params: { email: user.email } })
        .then(() => setNotifs(prev => prev.map(n => ({ ...n, read: true }))))
        .catch(() => {});
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/');
    setMobileOpen(false);
    setAccountOpen(false);
  };

  const isActive = (path: string) => location.pathname === path;

  const navLinks = [
    { to: '/', label: 'Home', icon: Home },
    { to: '/events', label: 'Events', icon: Calendar },
    { to: '/shop', label: 'Shop', icon: ShoppingBag },
  ];

  return (
    <header className="sticky top-0 z-50">
      <div className="glass border-b border-white/5 rounded-none">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            {/* Logo */}
            <Link to="/" className="flex items-center gap-2 group">
              <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center group-hover:scale-110 transition-transform">
                <Ticket className="w-5 h-5 text-white" />
              </div>
              <span className="text-lg font-bold gradient-text hidden sm:block">TicketHub</span>
            </Link>

            {/* Desktop Nav */}
            <nav className="hidden md:flex items-center gap-1">
              {navLinks.map(({ to, label, icon: Icon }) => (
                <Link
                  key={to}
                  to={to}
                  className={`flex items-center gap-1.5 px-3 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                    isActive(to)
                      ? 'bg-white/10 text-white'
                      : 'text-white/60 hover:text-white hover:bg-white/05'
                  }`}
                >
                  <Icon className="w-4 h-4" />
                  {label}
                </Link>
              ))}
            </nav>

            {/* Auth Actions */}
            <div className="hidden md:flex items-center gap-2">
              {user ? (
                <div className="flex items-center gap-2">
                  {/* Notification Bell */}
                  <div className="relative" ref={notifRef}>
                    <button
                      onClick={openNotifs}
                      className="relative flex items-center gap-1.5 px-3 py-2 rounded-lg text-sm text-white/40 hover:text-yellow-400 hover:bg-yellow-400/10 transition-all"
                      title="Notifications"
                    >
                      <Bell className="w-4 h-4" />
                      {unread > 0 && (
                        <span className="absolute top-1 right-1 w-4 h-4 bg-red-500 rounded-full text-[10px] font-bold text-white flex items-center justify-center">
                          {unread > 9 ? '9+' : unread}
                        </span>
                      )}
                    </button>

                    {notifOpen && (
                      <div className="absolute right-0 top-12 w-80 bg-[#13132b] rounded-2xl border border-white/10 shadow-2xl z-50 overflow-hidden fade-in">
                        <div className="px-4 py-3 border-b border-white/8 flex items-center justify-between">
                          <span className="text-white font-bold text-sm">Notifications</span>
                          {unread > 0 && <span className="text-xs text-white/30">{unread} unread</span>}
                        </div>
                        <div className="max-h-72 overflow-y-auto">
                          {notifs.length === 0 && (
                            <div className="px-4 py-8 text-center text-white/30 text-sm">No notifications yet</div>
                          )}
                          {notifs.map(n => (
                            <div key={n.id} className={`px-4 py-3 border-b border-white/5 transition-colors hover:bg-white/5 ${!n.read ? 'bg-purple-500/5' : ''}`}>
                              <div className="flex items-start gap-2">
                                {!n.read && <div className="w-1.5 h-1.5 rounded-full bg-purple-400 mt-1.5 shrink-0" />}
                                <div className={!n.read ? '' : 'pl-3.5'}>
                                  <div className="text-white text-sm font-semibold">{n.title}</div>
                                  <div className="text-white/50 text-xs mt-0.5 leading-relaxed">{n.message}</div>
                                  <div className="text-white/25 text-xs mt-1">{new Date(n.createdAt).toLocaleString()}</div>
                                </div>
                              </div>
                            </div>
                          ))}
                        </div>
                      </div>
                    )}
                  </div>

                  {/* Avatar + Account Dropdown */}
                  <div className="relative" ref={accountRef}>
                    <button
                      onClick={() => setAccountOpen(o => !o)}
                      className="w-9 h-9 rounded-full bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-sm font-bold text-white hover:scale-105 transition-transform ring-2 ring-transparent hover:ring-purple-400/50"
                    >
                      {user.fullName?.[0]?.toUpperCase()}
                    </button>

                    {accountOpen && (
                      <div className="absolute right-0 top-12 w-56 bg-[#13132b] rounded-2xl border border-white/10 shadow-2xl z-50 overflow-hidden fade-in">
                        {/* User info header */}
                        <div className="px-4 py-3 border-b border-white/10">
                          <div className="text-sm font-semibold text-white truncate">{user.fullName}</div>
                          <div className="text-xs text-white/40 truncate">{user.email}</div>
                        </div>

                        {/* Menu items */}
                        <div className="py-1">
                          <Link
                            to="/dashboard"
                            onClick={() => setAccountOpen(false)}
                            className="flex items-center gap-3 px-4 py-2.5 text-sm text-white/70 hover:text-white hover:bg-white/8 transition-colors"
                          >
                            <LayoutDashboard className="w-4 h-4 text-purple-400" />
                            Dashboard
                          </Link>
                          <Link
                            to="/bookings"
                            onClick={() => setAccountOpen(false)}
                            className="flex items-center gap-3 px-4 py-2.5 text-sm text-white/70 hover:text-white hover:bg-white/8 transition-colors"
                          >
                            <BookOpen className="w-4 h-4 text-purple-400" />
                            My Bookings
                          </Link>
                        </div>

                        <div className="border-t border-white/10 py-1">
                          <button
                            onClick={handleLogout}
                            className="flex items-center gap-3 px-4 py-2.5 w-full text-sm text-red-400 hover:text-red-300 hover:bg-red-400/10 transition-colors"
                          >
                            <LogOut className="w-4 h-4" />
                            Logout
                          </button>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              ) : (
                <>
                  <Link to="/login" className="btn-secondary text-sm py-2 px-4 flex items-center gap-1.5">
                    <LogIn className="w-4 h-4" /> Sign In
                  </Link>
                  <Link to="/register" className="btn-primary text-sm py-2 px-4 flex items-center gap-1.5">
                    <UserPlus className="w-4 h-4" /> Sign Up
                  </Link>
                </>
              )}
            </div>

            {/* Mobile Menu Toggle */}
            <button
              className="md:hidden p-2 rounded-lg glass"
              onClick={() => setMobileOpen(!mobileOpen)}
            >
              {mobileOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
            </button>
          </div>
        </div>

        {/* Mobile Menu */}
        {mobileOpen && (
          <div className="md:hidden px-4 pb-4 space-y-1 fade-in">
            {navLinks.map(({ to, label, icon: Icon }) => (
              <Link
                key={to}
                to={to}
                onClick={() => setMobileOpen(false)}
                className={`flex items-center gap-2 px-3 py-2.5 rounded-lg text-sm font-medium transition-all ${
                  isActive(to) ? 'bg-white/10 text-white' : 'text-white/60 hover:text-white hover:bg-white/05'
                }`}
              >
                <Icon className="w-4 h-4" />
                {label}
              </Link>
            ))}
            <div className="pt-2 border-t border-white/10">
              {user ? (
                <div className="space-y-1">
                  <div className="px-3 py-2 flex items-center gap-3">
                    <div className="w-8 h-8 rounded-full bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-sm font-bold text-white shrink-0">
                      {user.fullName?.[0]?.toUpperCase()}
                    </div>
                    <div>
                      <div className="text-sm font-semibold text-white">{user.fullName}</div>
                      <div className="text-xs text-white/40">{user.email}</div>
                    </div>
                  </div>
                  <Link to="/dashboard" onClick={() => setMobileOpen(false)} className="flex items-center gap-2 px-3 py-2.5 text-sm text-white/70 hover:text-white hover:bg-white/05 rounded-lg">
                    <LayoutDashboard className="w-4 h-4 text-purple-400" /> Dashboard
                  </Link>
                  <Link to="/bookings" onClick={() => setMobileOpen(false)} className="flex items-center gap-2 px-3 py-2.5 text-sm text-white/70 hover:text-white hover:bg-white/05 rounded-lg">
                    <BookOpen className="w-4 h-4 text-purple-400" /> My Bookings
                  </Link>
                  <button onClick={handleLogout} className="flex items-center gap-2 px-3 py-2.5 w-full text-sm text-red-400 hover:bg-red-400/10 rounded-lg">
                    <LogOut className="w-4 h-4" /> Logout
                  </button>
                </div>
              ) : (
                <div className="flex gap-2">
                  <Link to="/login" onClick={() => setMobileOpen(false)} className="flex-1 btn-secondary text-sm py-2 text-center">Sign In</Link>
                  <Link to="/register" onClick={() => setMobileOpen(false)} className="flex-1 btn-primary text-sm py-2 text-center">Sign Up</Link>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </header>
  );
}
