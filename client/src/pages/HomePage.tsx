import { Link } from 'react-router-dom';
import { Ticket, Zap, Shield, Star, ArrowRight, Music, Trophy, Theater, Tv } from 'lucide-react';

const categories = [
  { icon: Music, label: 'Concerts', slug: 'CONCERT', color: 'from-purple-500 to-pink-500', count: '2,400+' },
  { icon: Trophy, label: 'Sports', slug: 'SPORTS', color: 'from-blue-500 to-cyan-500', count: '800+' },
  { icon: Theater, label: 'Theatre', slug: 'THEATRE', color: 'from-amber-500 to-orange-500', count: '600+' },
  { icon: Tv, label: 'Festivals', slug: 'FESTIVAL', color: 'from-emerald-500 to-teal-500', count: '350+' },
];

const features = [
  { icon: Zap, title: 'Instant Booking', desc: 'Reserve your seats in seconds with our real-time booking system.' },
  { icon: Shield, title: 'Secure Payments', desc: 'Bank-grade encryption protects every transaction you make.' },
  { icon: Star, title: 'Best Seats', desc: 'Interactive seat maps let you pick exactly where you want to sit.' },
];

const heroCards = [
  { title: 'Taylor Swift', venue: 'SoFi Stadium', date: 'Jun 15', price: '$149' },
  { title: 'NBA Finals', venue: 'Chase Center', date: 'Jun 8', price: '$299' },
  { title: 'Hamilton', venue: 'Broadway', date: 'Jun 22', price: '$199' },
];

export default function HomePage() {
  return (
    <div className="min-h-screen">
      {/* Hero */}
      <section className="relative py-24 px-4 text-center overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-b from-purple-900/20 to-transparent" />
        <div className="relative max-w-4xl mx-auto slide-up">
          <div className="inline-flex items-center gap-2 glass px-4 py-2 rounded-full text-sm text-purple-300 mb-8">
            <div className="pulse-dot" />
            Live events happening now
          </div>
          <h1 className="text-5xl sm:text-7xl font-black mb-6 leading-tight tracking-tight">
            Your Next <br />
            <span className="gradient-text">Unforgettable</span> <br />
            Experience Awaits
          </h1>
          <p className="text-xl text-white/50 mb-10 max-w-2xl mx-auto leading-relaxed">
            Book tickets to world-class concerts, sports events, theatre shows, and festivals.
            Premium seats, instant confirmation, zero hassle.
          </p>
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <Link to="/events" className="btn-primary px-8 py-4 text-base flex items-center gap-2 rounded-xl">
              Browse Events <ArrowRight className="w-5 h-5" />
            </Link>
            <Link to="/register" className="btn-secondary px-8 py-4 text-base rounded-xl">
              Create Account
            </Link>
          </div>
        </div>

        {/* Floating preview cards */}
        <div className="relative mt-16 max-w-5xl mx-auto hidden lg:block">
          <div className="grid grid-cols-3 gap-6 px-8">
            {heroCards.map((card, i) => (
              <div key={i} className="glass card-hover p-5 text-left" style={{ animationDelay: `${i * 0.2}s` }}>
                <div className="flex items-start justify-between mb-3">
                  <div>
                    <div className="font-bold text-white">{card.title}</div>
                    <div className="text-sm text-white/40">{card.venue}</div>
                  </div>
                  <div className="badge badge-purple">{card.date}</div>
                </div>
                <div className="flex items-center justify-between">
                  <div className="text-xs text-white/30">From</div>
                  <div className="text-xl font-black gradient-text-gold">{card.price}</div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="py-16 px-4">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-white mb-3">Browse by Category</h2>
            <p className="text-white/40">Thousands of events across every genre</p>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            {categories.map(({ icon: Icon, label, slug, color, count }) => (
              <Link key={label} to={`/events?category=${slug}`} className="glass card-hover p-6 text-center group">
                <div className={`w-14 h-14 rounded-2xl bg-gradient-to-br ${color} flex items-center justify-center mx-auto mb-4 group-hover:scale-110 transition-transform`}>
                  <Icon className="w-7 h-7 text-white" />
                </div>
                <div className="font-semibold text-white mb-1">{label}</div>
                <div className="text-xs text-white/40">{count} events</div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-16 px-4">
        <div className="max-w-6xl mx-auto">
          <div className="glass-strong p-8 sm:p-12">
            <div className="text-center mb-12">
              <h2 className="text-3xl font-bold text-white mb-3">Why TicketHub?</h2>
              <p className="text-white/40">The smartest way to experience live events</p>
            </div>
            <div className="grid sm:grid-cols-3 gap-8">
              {features.map(({ icon: Icon, title, desc }) => (
                <div key={title} className="text-center">
                  <div className="w-16 h-16 rounded-2xl bg-gradient-to-br from-purple-500/20 to-pink-500/20 border border-purple-500/20 flex items-center justify-center mx-auto mb-4">
                    <Icon className="w-8 h-8 text-purple-400" />
                  </div>
                  <h3 className="text-lg font-bold text-white mb-2">{title}</h3>
                  <p className="text-white/40 text-sm leading-relaxed">{desc}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="py-16 px-4">
        <div className="max-w-3xl mx-auto text-center">
          <div className="glass p-10">
            <Ticket className="w-12 h-12 text-purple-400 mx-auto mb-4" />
            <h2 className="text-3xl font-bold text-white mb-4">Ready to book your next event?</h2>
            <p className="text-white/40 mb-8">Join thousands of fans discovering unforgettable experiences every day.</p>
            <Link to="/events" className="btn-primary px-10 py-4 text-base inline-flex items-center gap-2 rounded-xl">
              Explore Events <ArrowRight className="w-5 h-5" />
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
}
