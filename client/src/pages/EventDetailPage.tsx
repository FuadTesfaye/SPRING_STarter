import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Calendar, MapPin, Users, Ticket, X, CreditCard, ChevronRight,
  Minus, Plus, Check, AlertCircle, Clock
} from 'lucide-react';
import { eventApi, bookingApi, paymentApi, seatApi } from '../api/client';
import { useAuthStore } from '../store';
import type { TicketEvent, Booking, Seat } from '../types';

// ─── Seat Grid ────────────────────────────────────────────────
function SeatGrid({ eventId, onSeatSelect }: { eventId: string; onSeatSelect: (seat: string | null) => void }) {
  const [seats, setSeats] = useState<Seat[]>([]);
  const [selected, setSelected] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    seatApi.list(eventId)
      .then(res => setSeats(res.data))
      .catch(() => {
        // Generate mock seats if service unavailable
        const mock: Seat[] = [];
        const rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
        rows.forEach(row => {
          for (let i = 1; i <= 10; i++) {
            const seatNum = `${row}${i}`;
            const reserved = Math.random() < 0.3;
            mock.push({
              id: seatNum,
              eventId,
              seatNumber: seatNum,
              row,
              section: 'GENERAL',
              status: reserved ? 'RESERVED' : 'AVAILABLE',
            });
          }
        });
        setSeats(mock);
      })
      .finally(() => setLoading(false));
  }, [eventId]);

  const handleSelect = (seat: Seat) => {
    if (seat.status !== 'AVAILABLE') return;
    const newSelected = selected === seat.seatNumber ? null : seat.seatNumber;
    setSelected(newSelected);
    onSeatSelect(newSelected);
  };

  const rows = [...new Set(seats.map(s => s.row))].sort();

  if (loading) {
    return <div className="grid grid-cols-10 gap-1.5">{Array.from({ length: 80 }).map((_, i) => <div key={i} className="shimmer rounded aspect-square" />)}</div>;
  }

  return (
    <div>
      {/* Stage */}
      <div className="relative mb-6">
        <div className="h-8 bg-gradient-to-b from-white/10 to-transparent rounded-xl flex items-center justify-center">
          <span className="text-xs text-white/30 font-medium tracking-widest uppercase">Stage</span>
        </div>
      </div>

      {/* Seat rows */}
      <div className="space-y-1.5">
        {rows.map(row => (
          <div key={row} className="flex items-center gap-2">
            <div className="w-5 text-xs text-white/20 font-bold text-right shrink-0">{row}</div>
            <div className="flex gap-1 flex-1 justify-center">
              {seats.filter(s => s.row === row).map(seat => (
                <button
                  key={seat.seatNumber}
                  onClick={() => handleSelect(seat)}
                  title={seat.seatNumber}
                  className={
                    selected === seat.seatNumber
                      ? 'seat-selected'
                      : seat.status === 'AVAILABLE'
                      ? 'seat-available'
                      : 'seat-reserved'
                  }
                  style={{ width: 28 }}
                >
                  {selected === seat.seatNumber ? <Check className="w-3 h-3" /> : seat.seatNumber.replace(row, '')}
                </button>
              ))}
            </div>
          </div>
        ))}
      </div>

      {/* Legend */}
      <div className="flex items-center justify-center gap-6 mt-6 text-xs text-white/40">
        <div className="flex items-center gap-1.5"><div className="seat-available w-5 h-5" style={{ fontSize: 0 }} /> Available</div>
        <div className="flex items-center gap-1.5"><div className="seat-selected w-5 h-5" style={{ fontSize: 0 }} /> Selected</div>
        <div className="flex items-center gap-1.5"><div className="seat-reserved w-5 h-5" style={{ fontSize: 0 }} /> Taken</div>
      </div>
    </div>
  );
}

// ─── Booking Modal ────────────────────────────────────────────
function BookingModal({
  event,
  onClose,
}: {
  event: TicketEvent;
  onClose: () => void;
}) {
  const { user } = useAuthStore();
  const navigate = useNavigate();
  const [step, setStep] = useState<'seats' | 'checkout' | 'processing'>('seats');
  const [quantity, setQuantity] = useState(1);
  const [selectedSeat, setSelectedSeat] = useState<string | null>(null);
  const [cardToken, setCardToken] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('CARD');
  const [error, setError] = useState('');
  const [booking, setBooking] = useState<Booking | null>(null);

  const total = Number(event.ticketPrice) * quantity;

  const handleBooking = async () => {
    setStep('processing');
    setError('');
    try {
      const bookingRes = await bookingApi.create(event.id, quantity);
      const createdBooking: Booking = bookingRes.data;
      setBooking(createdBooking);

      // Reserve seat if selected
      if (selectedSeat) {
        await seatApi.reserve(event.id, createdBooking.id, selectedSeat).catch(() => {});
      }

      // Process payment
      const paymentRes = await paymentApi.process(
        createdBooking.id,
        createdBooking.totalAmount,
        paymentMethod,
        cardToken || undefined
      );

      const paymentStatus = paymentRes.data.status;
      navigate('/payment/result', {
        state: {
          status: paymentStatus === 'COMPLETED' ? 'success' : 'failed',
          booking: createdBooking,
          event: { title: event.title, venue: event.venue, eventDate: event.eventDate },
          amount: total,
          seat: selectedSeat,
        },
      });
    } catch (err: any) {
      setError(err.response?.data?.error || 'Booking failed. Please try again.');
      setStep('checkout');
    }
  };

  if (!user) {
    return (
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm fade-in">
        <div className="glass-strong p-8 max-w-sm w-full text-center scale-in">
          <AlertCircle className="w-12 h-12 text-amber-400 mx-auto mb-4" />
          <h3 className="text-xl font-bold text-white mb-2">Sign in Required</h3>
          <p className="text-white/40 mb-6">Create an account or sign in to book tickets.</p>
          <div className="flex gap-3">
            <button onClick={onClose} className="btn-secondary flex-1">Cancel</button>
            <button onClick={() => navigate('/login')} className="btn-primary flex-1">Sign In</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/70 backdrop-blur-md fade-in">
      <div className="glass-strong w-full sm:max-w-2xl max-h-[92vh] overflow-y-auto scrollbar-hide rounded-t-3xl sm:rounded-3xl scale-in">
        {/* Modal Header */}
        <div className="flex items-center justify-between p-6 border-b border-white/10 sticky top-0 glass-strong z-10 rounded-t-3xl">
          <div>
            <h2 className="text-xl font-bold text-white">{event.title}</h2>
            <p className="text-sm text-white/40">{event.venue}</p>
          </div>
          <button onClick={onClose} className="w-9 h-9 rounded-full glass flex items-center justify-center hover:bg-white/10">
            <X className="w-5 h-5 text-white/60" />
          </button>
        </div>

        <div className="p-6">
          {/* Step indicator */}
          <div className="flex items-center gap-2 mb-8">
            {['seats', 'checkout'].map((s, i) => (
              <div key={s} className="flex items-center gap-2">
                <div className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold ${
                  step === s || (step === 'processing' && s === 'checkout')
                    ? 'bg-gradient-to-br from-purple-500 to-pink-500 text-white'
                    : (step === 'checkout' && s === 'seats') || step === 'processing'
                    ? 'bg-green-500/20 text-green-400 border border-green-500/30'
                    : 'glass text-white/30'
                }`}>
                  {((step === 'checkout' && s === 'seats') || step === 'processing') && s === 'seats' ? <Check className="w-3.5 h-3.5" /> : i + 1}
                </div>
                <span className={`text-sm font-medium capitalize ${step === s ? 'text-white' : 'text-white/30'}`}>{s}</span>
                {i < 1 && <ChevronRight className="w-4 h-4 text-white/20" />}
              </div>
            ))}
          </div>

          {/* STEP 1: Seat Selection */}
          {step === 'seats' && (
            <div className="slide-up">
              {/* Quantity selector */}
              <div className="glass p-4 mb-6 flex items-center justify-between">
                <div>
                  <div className="font-medium text-white">Tickets</div>
                  <div className="text-sm text-white/40">${Number(event.ticketPrice).toFixed(2)} each</div>
                </div>
                <div className="flex items-center gap-3">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="w-8 h-8 rounded-full glass flex items-center justify-center hover:bg-white/10 transition-all"
                  >
                    <Minus className="w-4 h-4" />
                  </button>
                  <span className="text-xl font-bold text-white w-6 text-center">{quantity}</span>
                  <button
                    onClick={() => setQuantity(Math.min(10, quantity + 1))}
                    className="w-8 h-8 rounded-full glass flex items-center justify-center hover:bg-white/10 transition-all"
                  >
                    <Plus className="w-4 h-4" />
                  </button>
                </div>
              </div>

              {/* Seat map */}
              <div className="mb-6">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="font-semibold text-white">Select a Seat</h3>
                  {selectedSeat && (
                    <div className="badge badge-green">Seat {selectedSeat} selected</div>
                  )}
                </div>
                <SeatGrid eventId={event.id} onSeatSelect={setSelectedSeat} />
              </div>

              {/* Total */}
              <div className="glass p-4 mb-6 flex items-center justify-between">
                <span className="text-white/60">Total</span>
                <span className="text-2xl font-black gradient-text-gold">${total.toFixed(2)}</span>
              </div>

              <button onClick={() => setStep('checkout')} className="btn-primary w-full py-4 text-base rounded-xl flex items-center justify-center gap-2">
                Continue to Checkout <ChevronRight className="w-5 h-5" />
              </button>
            </div>
          )}

          {/* STEP 2: Checkout */}
          {(step === 'checkout' || step === 'processing') && (
            <div className="slide-up">
              <div className="glass p-5 mb-4">
                <h3 className="font-semibold text-white mb-3">Order Summary</h3>
                <div className="space-y-2 text-sm">
                  <div className="flex justify-between text-white/60">
                    <span>{event.title}</span>
                    <span>{quantity}x</span>
                  </div>
                  {selectedSeat && (
                    <div className="flex justify-between text-white/60">
                      <span>Seat</span>
                      <span>{selectedSeat}</span>
                    </div>
                  )}
                  <div className="flex justify-between font-bold text-white pt-2 border-t border-white/10">
                    <span>Total</span>
                    <span className="gradient-text-gold">${total.toFixed(2)}</span>
                  </div>
                </div>
              </div>

              <div className="space-y-4 mb-6">
                <div>
                  <label className="text-sm text-white/60 mb-2 block">Payment Method</label>
                  <div className="grid grid-cols-2 gap-3">
                    {['CARD', 'PAYPAL'].map(m => (
                      <button
                        key={m}
                        onClick={() => setPaymentMethod(m)}
                        className={`p-3 rounded-xl text-sm font-medium transition-all ${
                          paymentMethod === m ? 'border border-purple-500 bg-purple-500/10 text-purple-300' : 'glass text-white/40 hover:text-white'
                        }`}
                      >
                        {m === 'CARD' ? '💳 Card' : '🅿️ PayPal'}
                      </button>
                    ))}
                  </div>
                </div>

                {paymentMethod === 'CARD' && (
                  <div>
                    <label className="text-sm text-white/60 mb-2 block">Card Token / Last 4 digits</label>
                    <div className="relative">
                      <CreditCard className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
                      <input
                        type="text"
                        placeholder="e.g. 4242 (use 0000 to test failure)"
                        value={cardToken}
                        onChange={e => setCardToken(e.target.value)}
                        className="input-field pl-10"
                        maxLength={4}
                      />
                    </div>
                    <p className="text-xs text-white/20 mt-1">Demo: any digits = success, 0000 = failure simulation</p>
                  </div>
                )}
              </div>

              {error && (
                <div className="glass border border-red-500/30 bg-red-500/10 p-3 rounded-xl mb-4 flex items-start gap-2 fade-in">
                  <AlertCircle className="w-4 h-4 text-red-400 shrink-0 mt-0.5" />
                  <span className="text-sm text-red-300">{error}</span>
                </div>
              )}

              <div className="flex gap-3">
                <button
                  onClick={() => { setStep('seats'); setError(''); }}
                  disabled={step === 'processing'}
                  className="btn-secondary flex-1 py-4 rounded-xl"
                >
                  Back
                </button>
                <button
                  onClick={handleBooking}
                  disabled={step === 'processing'}
                  className="btn-primary flex-1 py-4 rounded-xl flex items-center justify-center gap-2"
                >
                  {step === 'processing' ? (
                    <>
                      <Clock className="w-4 h-4 animate-spin" />
                      Processing...
                    </>
                  ) : (
                    <>
                      <Ticket className="w-4 h-4" />
                      Pay ${total.toFixed(2)}
                    </>
                  )}
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

// ─── Event Detail Page ────────────────────────────────────────
export default function EventDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [event, setEvent] = useState<TicketEvent | null>(null);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    if (!id) return;
    eventApi.list()
      .then(res => {
        const found = res.data.find((e: TicketEvent) => e.id === id);
        setEvent(found || null);
      })
      .catch(() => setEvent(null))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="max-w-6xl mx-auto px-4 py-10 space-y-6">
        <div className="h-64 shimmer rounded-3xl" />
        <div className="grid lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 space-y-4">
            <div className="h-8 shimmer rounded-xl w-2/3" />
            <div className="h-4 shimmer rounded-xl w-1/2" />
            <div className="h-32 shimmer rounded-xl" />
          </div>
          <div className="h-64 shimmer rounded-xl" />
        </div>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="max-w-6xl mx-auto px-4 py-20 text-center">
        <div className="text-6xl mb-4">🎟️</div>
        <h2 className="text-2xl font-bold text-white mb-2">Event not found</h2>
        <p className="text-white/40">This event may have ended or been removed.</p>
      </div>
    );
  }

  const availPct = Math.round((event.availableSeats / event.totalSeats) * 100);

  return (
    <>
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        {/* Hero image */}
        <div className="relative h-72 sm:h-96 rounded-3xl overflow-hidden mb-8 slide-up">
          {event.imageUrl ? (
            <img src={event.imageUrl} alt={event.title} className="w-full h-full object-cover" />
          ) : (
            <div className="w-full h-full bg-gradient-to-br from-purple-900 to-slate-900 flex items-center justify-center">
              <div className="text-8xl opacity-30">🎟️</div>
            </div>
          )}
          <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/30 to-transparent" />
          <div className="absolute bottom-6 left-6">
            <div className="badge badge-purple mb-2">{event.category}</div>
            <h1 className="text-4xl font-black text-white drop-shadow-xl">{event.title}</h1>
          </div>
        </div>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Left — Event Info */}
          <div className="lg:col-span-2 space-y-6 slide-up">
            {/* Meta */}
            <div className="glass p-6 grid sm:grid-cols-3 gap-4">
              {[
                { icon: Calendar, label: 'Date & Time', value: new Date(event.eventDate).toLocaleString('en-US', { weekday: 'short', month: 'long', day: 'numeric', year: 'numeric', hour: 'numeric', minute: '2-digit' }) },
                { icon: MapPin, label: 'Venue', value: event.venue },
                { icon: Users, label: 'Seats Available', value: `${event.availableSeats.toLocaleString()} / ${event.totalSeats.toLocaleString()}` },
              ].map(({ icon: Icon, label, value }) => (
                <div key={label} className="flex items-start gap-3">
                  <div className="w-9 h-9 rounded-xl bg-purple-500/20 flex items-center justify-center shrink-0">
                    <Icon className="w-4.5 h-4.5 text-purple-400" style={{ width: 18, height: 18 }} />
                  </div>
                  <div>
                    <div className="text-xs text-white/40 mb-0.5">{label}</div>
                    <div className="text-sm font-medium text-white">{value}</div>
                  </div>
                </div>
              ))}
            </div>

            {/* Description */}
            <div className="glass p-6">
              <h2 className="text-lg font-bold text-white mb-3">About this Event</h2>
              <p className="text-white/50 leading-relaxed">{event.description}</p>
            </div>

            {/* Availability */}
            <div className="glass p-6">
              <div className="flex justify-between items-center mb-3">
                <h2 className="text-lg font-bold text-white">Seat Availability</h2>
                <span className={`badge ${availPct < 20 ? 'badge-red' : 'badge-green'}`}>
                  {availPct < 20 ? 'Almost Sold Out' : 'Available'}
                </span>
              </div>
              <div className="h-3 rounded-full bg-white/10 overflow-hidden">
                <div
                  className={`h-full rounded-full ${availPct < 20 ? 'bg-red-400' : 'bg-gradient-to-r from-purple-500 to-pink-500'}`}
                  style={{ width: `${availPct}%`, transition: 'width 1s ease' }}
                />
              </div>
              <div className="flex justify-between text-xs text-white/30 mt-2">
                <span>{event.availableSeats.toLocaleString()} remaining</span>
                <span>{availPct}% available</span>
              </div>
            </div>
          </div>

          {/* Right — Booking Card */}
          <div className="slide-up" style={{ animationDelay: '0.15s' }}>
            <div className="glass-strong p-6 sticky top-24">
              <div className="text-center mb-6">
                <div className="text-xs text-white/30 mb-1">Starting from</div>
                <div className="text-5xl font-black gradient-text-gold">${Number(event.ticketPrice).toFixed(2)}</div>
                <div className="text-sm text-white/30 mt-1">per ticket</div>
              </div>

              <div className="space-y-3 mb-6 text-sm">
                <div className="flex items-center justify-between text-white/50">
                  <span>Event Date</span>
                  <span className="text-white font-medium">{new Date(event.eventDate).toLocaleDateString()}</span>
                </div>
                <div className="flex items-center justify-between text-white/50">
                  <span>Venue</span>
                  <span className="text-white font-medium text-right text-xs">{event.venue.split(',')[0]}</span>
                </div>
                <div className="flex items-center justify-between text-white/50">
                  <span>Available</span>
                  <span className="text-white font-medium">{event.availableSeats} seats</span>
                </div>
              </div>

              <button
                onClick={() => setShowModal(true)}
                disabled={event.availableSeats === 0}
                className={`w-full py-4 rounded-xl font-bold text-base flex items-center justify-center gap-2 ${
                  event.availableSeats === 0 ? 'bg-white/10 text-white/30 cursor-not-allowed' : 'btn-primary'
                }`}
              >
                <Ticket className="w-5 h-5" />
                {event.availableSeats === 0 ? 'Sold Out' : 'Book Now'}
              </button>

              <p className="text-center text-xs text-white/20 mt-3">Secure checkout · Instant confirmation</p>
            </div>
          </div>
        </div>
      </div>

      {showModal && (
        <BookingModal event={event} onClose={() => setShowModal(false)} />
      )}
    </>
  );
}
