import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Ticket, Calendar, ArrowRight, Search } from 'lucide-react';
import { bookingApi } from '../api/client';
import type { Booking } from '../types';

export default function BookingHistoryPage() {
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [filter, setFilter] = useState<string>('ALL');

  useEffect(() => {
    bookingApi.myBookings()
      .then(res => setBookings(res.data))
      .catch(() => setBookings([]))
      .finally(() => setLoading(false));
  }, []);

  const filtered = bookings.filter(b => {
    const matchSearch = b.id.toLowerCase().includes(search.toLowerCase());
    const matchFilter = filter === 'ALL' || b.status === filter;
    return matchSearch && matchFilter;
  });

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-8 slide-up">
        <h1 className="text-4xl font-black text-white mb-2">My Bookings</h1>
        <p className="text-white/40">All your ticket bookings in one place</p>
      </div>

      {/* Search and filter */}
      <div className="glass p-4 mb-6 flex flex-col sm:flex-row gap-3 slide-up" style={{ animationDelay: '0.1s' }}>
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
          <input
            type="text"
            placeholder="Search by booking ID..."
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="input-field pl-10"
          />
        </div>
        <div className="flex gap-2">
          {['ALL', 'PENDING', 'CONFIRMED', 'CANCELLED'].map(s => (
            <button
              key={s}
              onClick={() => setFilter(s)}
              className={`px-3 py-2 rounded-lg text-xs font-medium transition-all ${
                filter === s ? 'bg-purple-500/20 text-purple-300 border border-purple-500/30' : 'glass text-white/40 hover:text-white'
              }`}
            >
              {s}
            </button>
          ))}
        </div>
      </div>

      {loading ? (
        <div className="space-y-4">
          {Array.from({ length: 4 }).map((_, i) => <div key={i} className="h-24 shimmer rounded-2xl" />)}
        </div>
      ) : filtered.length === 0 ? (
        <div className="text-center py-20 glass rounded-2xl">
          <Ticket className="w-16 h-16 text-white/10 mx-auto mb-4" />
          <h3 className="text-xl font-bold text-white mb-2">
            {bookings.length === 0 ? 'No bookings yet' : 'No results found'}
          </h3>
          <p className="text-white/30 mb-6">
            {bookings.length === 0 ? 'Start exploring events and book your first ticket!' : 'Try adjusting your filters'}
          </p>
          {bookings.length === 0 && (
            <Link to="/events" className="btn-primary px-6 py-3 text-sm rounded-xl inline-flex items-center gap-2">
              Browse Events <ArrowRight className="w-4 h-4" />
            </Link>
          )}
        </div>
      ) : (
        <div className="space-y-4 slide-up" style={{ animationDelay: '0.15s' }}>
          {filtered.map((booking, i) => (
            <div key={booking.id} className="glass p-5 slide-up" style={{ animationDelay: `${i * 0.05}s` }}>
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
                <div className="flex items-center gap-4">
                  <div className="w-12 h-12 rounded-2xl bg-gradient-to-br from-purple-500/20 to-pink-500/20 flex items-center justify-center shrink-0">
                    <Ticket className="w-6 h-6 text-purple-400" />
                  </div>
                  <div>
                    <div className="font-bold text-white">
                      Booking #{booking.id.slice(0, 12)}...
                    </div>
                    <div className="flex items-center gap-3 text-sm text-white/40 mt-1">
                      <span className="flex items-center gap-1">
                        <Calendar className="w-3.5 h-3.5" />
                        {new Date(booking.createdAt).toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}
                      </span>
                      <span>·</span>
                      <span>{booking.quantity} ticket{booking.quantity !== 1 ? 's' : ''}</span>
                    </div>
                  </div>
                </div>

                <div className="flex items-center gap-4 sm:text-right">
                  <div>
                    <div className="text-lg font-black text-white">${Number(booking.totalAmount).toFixed(2)}</div>
                    <div className="text-xs text-white/30">Total paid</div>
                  </div>
                  <span className={`badge ${
                    booking.status === 'CONFIRMED' ? 'badge-green'
                    : booking.status === 'CANCELLED' ? 'badge-red'
                    : 'badge-yellow'
                  }`}>
                    {booking.status}
                  </span>
                </div>
              </div>

              {/* Full booking ID */}
              <div className="mt-3 pt-3 border-t border-white/05 text-xs text-white/20 font-mono">
                ID: {booking.id}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
