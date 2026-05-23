import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Ticket, Calendar, DollarSign, TrendingUp, ArrowRight, BookOpen } from 'lucide-react';
import { bookingApi } from '../api/client';
import { useAuthStore } from '../store';
import type { Booking } from '../types';

export default function DashboardPage() {
  const { user } = useAuthStore();
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    bookingApi.myBookings()
      .then(res => setBookings(res.data))
      .catch(() => setBookings([]))
      .finally(() => setLoading(false));
  }, []);

  const totalSpent = bookings.reduce((sum, b) => sum + Number(b.totalAmount), 0);
  const confirmedCount = bookings.filter(b => b.status === 'CONFIRMED').length;
  const pendingCount = bookings.filter(b => b.status === 'PENDING').length;

  const stats = [
    { icon: BookOpen, label: 'Total Bookings', value: bookings.length, color: 'text-purple-400', bg: 'from-purple-500/20 to-purple-500/5' },
    { icon: Calendar, label: 'Confirmed', value: confirmedCount, color: 'text-green-400', bg: 'from-green-500/20 to-green-500/5' },
    { icon: TrendingUp, label: 'Pending', value: pendingCount, color: 'text-yellow-400', bg: 'from-yellow-500/20 to-yellow-500/5' },
    { icon: DollarSign, label: 'Total Spent', value: `$${totalSpent.toFixed(2)}`, color: 'text-pink-400', bg: 'from-pink-500/20 to-pink-500/5' },
  ];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-10 slide-up">
        <div className="flex items-center gap-4 mb-2">
          <div className="w-14 h-14 rounded-2xl bg-gradient-to-br from-purple-500 to-pink-600 flex items-center justify-center text-2xl font-black text-white">
            {user?.fullName?.[0]?.toUpperCase()}
          </div>
          <div>
            <h1 className="text-3xl font-black text-white">Welcome back, {user?.fullName?.split(' ')[0]}!</h1>
            <p className="text-white/40">{user?.email}</p>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-10">
        {stats.map(({ icon: Icon, label, value, color, bg }, i) => (
          <div key={label} className="glass p-5 slide-up" style={{ animationDelay: `${i * 0.08}s` }}>
            <div className={`w-10 h-10 rounded-xl bg-gradient-to-br ${bg} flex items-center justify-center mb-3`}>
              <Icon className={`w-5 h-5 ${color}`} />
            </div>
            <div className={`text-2xl font-black ${color} mb-1`}>{loading ? '—' : value}</div>
            <div className="text-xs text-white/40">{label}</div>
          </div>
        ))}
      </div>

      <div className="glass p-6 mb-8 slide-up" style={{ animationDelay: '0.3s' }}>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-bold text-white">Recent Bookings</h2>
          <Link to="/bookings" className="text-sm text-purple-400 hover:text-purple-300 flex items-center gap-1">
            View all <ArrowRight className="w-4 h-4" />
          </Link>
        </div>

        {loading ? (
          <div className="space-y-3">{Array.from({ length: 3 }).map((_, i) => <div key={i} className="h-16 shimmer rounded-xl" />)}</div>
        ) : bookings.length === 0 ? (
          <div className="text-center py-12">
            <Ticket className="w-12 h-12 text-white/10 mx-auto mb-3" />
            <p className="text-white/30 mb-4">No bookings yet</p>
            <Link to="/events" className="btn-primary px-6 py-3 text-sm rounded-xl inline-flex items-center gap-2">
              Browse Events <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        ) : (
          <div className="space-y-3">
            {bookings.slice(0, 5).map(booking => (
              <div key={booking.id} className="flex items-center justify-between p-4 rounded-xl bg-white/[0.03] border border-white/[0.05] hover:bg-white/[0.05] transition-all">
                <div className="flex items-center gap-3">
                  <div className="w-9 h-9 rounded-xl bg-purple-500/20 flex items-center justify-center">
                    <Ticket className="w-4 h-4 text-purple-400" />
                  </div>
                  <div>
                    <div className="text-sm font-medium text-white">#{booking.id.slice(0, 8)}...</div>
                    <div className="text-xs text-white/40">{booking.quantity} ticket{booking.quantity !== 1 ? 's' : ''} · {new Date(booking.createdAt).toLocaleDateString()}</div>
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  <div className="text-sm font-bold text-white">${Number(booking.totalAmount).toFixed(2)}</div>
                  <span className={`badge text-xs ${booking.status === 'CONFIRMED' ? 'badge-green' : booking.status === 'CANCELLED' ? 'badge-red' : 'badge-yellow'}`}>
                    {booking.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      <div className="grid sm:grid-cols-2 gap-4 slide-up" style={{ animationDelay: '0.4s' }}>
        <Link to="/events" className="glass card-hover p-6 flex items-center gap-4 group">
          <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-purple-500 to-pink-500 flex items-center justify-center group-hover:scale-110 transition-transform">
            <Calendar className="w-6 h-6 text-white" />
          </div>
          <div>
            <div className="font-bold text-white">Browse Events</div>
            <div className="text-sm text-white/40">Find your next experience</div>
          </div>
          <ArrowRight className="w-5 h-5 text-white/20 ml-auto group-hover:text-purple-400 transition-colors" />
        </Link>
        <Link to="/bookings" className="glass card-hover p-6 flex items-center gap-4 group">
          <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-blue-500 to-cyan-500 flex items-center justify-center group-hover:scale-110 transition-transform">
            <BookOpen className="w-6 h-6 text-white" />
          </div>
          <div>
            <div className="font-bold text-white">My Bookings</div>
            <div className="text-sm text-white/40">View all your tickets</div>
          </div>
          <ArrowRight className="w-5 h-5 text-white/20 ml-auto group-hover:text-blue-400 transition-colors" />
        </Link>
      </div>
    </div>
  );
}
