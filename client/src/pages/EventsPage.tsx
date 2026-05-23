import { useEffect, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Calendar, MapPin, Tag, Users, Search, SlidersHorizontal } from 'lucide-react';
import { eventApi } from '../api/client';
import type { TicketEvent } from '../types';

const CATEGORIES = ['ALL', 'CONCERT', 'SPORTS', 'THEATRE', 'FESTIVAL', 'CONFERENCE'];

const categoryColors: Record<string, string> = {
  CONCERT: 'badge-purple',
  SPORTS: 'badge-green',
  THEATRE: 'badge-yellow',
  FESTIVAL: 'badge-red',
  CONFERENCE: 'badge-purple',
};

function EventCard({ event }: { event: TicketEvent }) {
  const availPct = Math.round((event.availableSeats / event.totalSeats) * 100);
  const isLowStock = availPct < 20;

  return (
    <Link to={`/events/${event.id}`} className="glass card-hover block overflow-hidden group">
      <div className="relative h-48 overflow-hidden rounded-t-2xl bg-gradient-to-br from-purple-900 to-slate-900">
        {event.imageUrl ? (
          <img
            src={event.imageUrl}
            alt={event.title}
            className="w-full h-full object-cover opacity-60 group-hover:opacity-80 group-hover:scale-105 transition-all duration-500"
          />
        ) : (
          <div className="w-full h-full flex items-center justify-center">
            <div className="text-5xl opacity-20">🎟️</div>
          </div>
        )}
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />
        <div className="absolute top-3 left-3">
          <span className={`badge ${categoryColors[event.category] || 'badge-purple'}`}>
            {event.category}
          </span>
        </div>
        {isLowStock && (
          <div className="absolute top-3 right-3">
            <span className="badge badge-red">Almost Sold Out</span>
          </div>
        )}
        <div className="absolute bottom-3 right-3">
          <div className="text-2xl font-black text-white drop-shadow-lg">
            ${Number(event.ticketPrice).toFixed(0)}
          </div>
        </div>
      </div>

      <div className="p-5">
        <h3 className="text-lg font-bold text-white mb-3 group-hover:gradient-text transition-all line-clamp-1">
          {event.title}
        </h3>
        <div className="space-y-2">
          <div className="flex items-center gap-2 text-sm text-white/50">
            <MapPin className="w-3.5 h-3.5 text-purple-400 shrink-0" />
            <span className="truncate">{event.venue}</span>
          </div>
          <div className="flex items-center gap-2 text-sm text-white/50">
            <Calendar className="w-3.5 h-3.5 text-purple-400 shrink-0" />
            <span>{new Date(event.eventDate).toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric', year: 'numeric' })}</span>
          </div>
          <div className="flex items-center gap-2 text-sm text-white/50">
            <Users className="w-3.5 h-3.5 text-purple-400 shrink-0" />
            <span>{event.availableSeats.toLocaleString()} seats available</span>
          </div>
        </div>

        {/* Availability bar */}
        <div className="mt-4">
          <div className="flex justify-between text-xs text-white/30 mb-1">
            <span>Availability</span>
            <span>{availPct}%</span>
          </div>
          <div className="h-1.5 rounded-full bg-white/10 overflow-hidden">
            <div
              className={`h-full rounded-full transition-all ${isLowStock ? 'bg-red-400' : 'bg-gradient-to-r from-purple-500 to-pink-500'}`}
              style={{ width: `${availPct}%` }}
            />
          </div>
        </div>
      </div>
    </Link>
  );
}

function SkeletonCard() {
  return (
    <div className="glass overflow-hidden">
      <div className="h-48 shimmer rounded-t-2xl" />
      <div className="p-5 space-y-3">
        <div className="h-5 shimmer rounded-lg w-3/4" />
        <div className="h-4 shimmer rounded-lg w-1/2" />
        <div className="h-4 shimmer rounded-lg w-2/3" />
        <div className="h-4 shimmer rounded-lg w-1/2" />
      </div>
    </div>
  );
}

export default function EventsPage() {
  const [events, setEvents] = useState<TicketEvent[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [searchParams, setSearchParams] = useSearchParams();

  const activeCategory = searchParams.get('category') || 'ALL';

  useEffect(() => {
    setLoading(true);
    const cat = activeCategory === 'ALL' ? undefined : activeCategory;
    eventApi.list(cat)
      .then(res => {
        const hidden = new Set<string>(JSON.parse(localStorage.getItem('admin_hidden_events') || '[]'));
        const custom: TicketEvent[] = JSON.parse(localStorage.getItem('admin_custom_events') || '[]');
        const backendIds = new Set(res.data.map((e: TicketEvent) => e.id));
        // Merge: backend events (not hidden, not overridden) + custom events (not hidden)
        const overrideIds = new Set(custom.map(c => c.id));
        const base = res.data.filter((e: TicketEvent) => !hidden.has(e.id) && !overrideIds.has(e.id));
        const customVisible = custom.filter(c => !hidden.has(c.id) && (cat ? c.category === cat : true));
        setEvents([...base, ...customVisible]);
      })
      .catch(() => setEvents([]))
      .finally(() => setLoading(false));
  }, [activeCategory]);

  const filtered = events.filter(e =>
    e.title.toLowerCase().includes(search.toLowerCase()) ||
    e.venue.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      {/* Header */}
      <div className="mb-8 slide-up">
        <h1 className="text-4xl font-black text-white mb-2">Upcoming Events</h1>
        <p className="text-white/40">Discover and book amazing live experiences</p>
      </div>

      {/* Search + Filter Bar */}
      <div className="glass p-4 mb-6 flex flex-col sm:flex-row gap-4 slide-up" style={{ animationDelay: '0.1s' }}>
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
          <input
            type="text"
            placeholder="Search events, venues..."
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="input-field pl-10"
          />
        </div>
        <div className="flex items-center gap-2">
          <SlidersHorizontal className="w-4 h-4 text-white/30" />
          <select
            value={activeCategory}
            onChange={e => setSearchParams(e.target.value === 'ALL' ? {} : { category: e.target.value })}
            className="input-field w-auto min-w-[140px]"
          >
            {CATEGORIES.map(c => (
              <option key={c} value={c} style={{ background: '#0f1628' }}>{c === 'ALL' ? 'All Categories' : c}</option>
            ))}
          </select>
        </div>
      </div>

      {/* Category pills */}
      <div className="flex gap-2 mb-8 overflow-x-auto pb-2 scrollbar-hide slide-up" style={{ animationDelay: '0.15s' }}>
        {CATEGORIES.map(cat => (
          <button
            key={cat}
            onClick={() => setSearchParams(cat === 'ALL' ? {} : { category: cat })}
            className={`shrink-0 px-4 py-2 rounded-full text-sm font-medium transition-all ${
              activeCategory === cat
                ? 'bg-gradient-to-r from-purple-500 to-pink-500 text-white shadow-lg shadow-purple-500/25'
                : 'glass text-white/50 hover:text-white hover:bg-white/10'
            }`}
          >
            {cat === 'ALL' ? 'All Events' : cat}
          </button>
        ))}
      </div>

      {/* Results count */}
      {!loading && (
        <div className="text-sm text-white/30 mb-6 fade-in">
          {filtered.length} event{filtered.length !== 1 ? 's' : ''} found
        </div>
      )}

      {/* Grid */}
      <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {loading
          ? Array.from({ length: 6 }).map((_, i) => <SkeletonCard key={i} />)
          : filtered.length > 0
          ? filtered.map(event => <EventCard key={event.id} event={event} />)
          : (
            <div className="col-span-full text-center py-20">
              <div className="text-6xl mb-4">🎟️</div>
              <h3 className="text-xl font-bold text-white mb-2">No events found</h3>
              <p className="text-white/40">Try adjusting your search or filters</p>
            </div>
          )}
      </div>
    </div>
  );
}
