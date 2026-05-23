import { useLocation, Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { CheckCircle, XCircle, ArrowRight, Ticket, Download, Home } from 'lucide-react';
import type { Booking } from '../types';

interface LocationState {
  status: 'success' | 'failed';
  booking: Booking;
  event: { title: string; venue: string; eventDate: string };
  amount: number;
  seat?: string;
}

export default function PaymentResultPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const state = location.state as LocationState | null;
  const [animationDone, setAnimationDone] = useState(false);

  useEffect(() => {
    if (!state) {
      navigate('/events');
      return;
    }
    const t = setTimeout(() => setAnimationDone(true), 800);
    return () => clearTimeout(t);
  }, [state, navigate]);

  if (!state) return null;

  const isSuccess = state.status === 'success';

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        {/* Result animation */}
        <div className="text-center mb-8">
          {isSuccess ? (
            <div className="success-ring inline-block">
              <div className="w-28 h-28 rounded-full bg-gradient-to-br from-green-400/20 to-emerald-600/20 border-2 border-green-400/30 flex items-center justify-center mx-auto">
                <CheckCircle className="w-14 h-14 text-green-400" />
              </div>
            </div>
          ) : (
            <div className="fail-shake inline-block">
              <div className="w-28 h-28 rounded-full bg-gradient-to-br from-red-400/20 to-red-600/20 border-2 border-red-400/30 flex items-center justify-center mx-auto">
                <XCircle className="w-14 h-14 text-red-400" />
              </div>
            </div>
          )}

          <div className="mt-6 slide-up">
            <h1 className={`text-3xl font-black ${isSuccess ? 'text-green-400' : 'text-red-400'} mb-2`}>
              {isSuccess ? 'Booking Confirmed!' : 'Payment Failed'}
            </h1>
            <p className="text-white/50">
              {isSuccess
                ? 'Your tickets have been reserved successfully.'
                : 'Something went wrong with your payment. Please try again.'}
            </p>
          </div>
        </div>

        {/* Ticket card */}
        {isSuccess && animationDone && (
          <div className="glass-strong p-6 mb-6 slide-up relative overflow-hidden">
            {/* Decorative circles */}
            <div className="absolute -left-4 top-1/2 -translate-y-1/2 w-8 h-8 bg-[#050a14] rounded-full border-r border-white/10" />
            <div className="absolute -right-4 top-1/2 -translate-y-1/2 w-8 h-8 bg-[#050a14] rounded-full border-l border-white/10" />

            <div className="flex items-start justify-between mb-4">
              <div>
                <div className="badge badge-green mb-2">Confirmed</div>
                <h2 className="text-xl font-bold text-white">{state.event.title}</h2>
              </div>
              <Ticket className="w-8 h-8 text-purple-400 shrink-0" />
            </div>

            <div className="border-t border-dashed border-white/10 my-4" />

            <div className="grid grid-cols-2 gap-4 text-sm">
              <div>
                <div className="text-white/30 mb-1">Venue</div>
                <div className="text-white font-medium">{state.event.venue.split(',')[0]}</div>
              </div>
              <div>
                <div className="text-white/30 mb-1">Date</div>
                <div className="text-white font-medium">{new Date(state.event.eventDate).toLocaleDateString()}</div>
              </div>
              {state.seat && (
                <div>
                  <div className="text-white/30 mb-1">Seat</div>
                  <div className="text-white font-bold">{state.seat}</div>
                </div>
              )}
              <div>
                <div className="text-white/30 mb-1">Tickets</div>
                <div className="text-white font-medium">{state.booking.quantity}x</div>
              </div>
            </div>

            <div className="border-t border-dashed border-white/10 my-4" />

            <div className="flex items-center justify-between">
              <div>
                <div className="text-white/30 text-xs">Booking ID</div>
                <div className="text-white text-xs font-mono">{state.booking.id.slice(0, 16)}...</div>
              </div>
              <div className="text-right">
                <div className="text-white/30 text-xs">Total Paid</div>
                <div className="text-2xl font-black gradient-text-gold">${Number(state.amount).toFixed(2)}</div>
              </div>
            </div>
          </div>
        )}

        {!isSuccess && animationDone && (
          <div className="glass border border-red-500/20 p-5 mb-6 slide-up">
            <p className="text-white/50 text-sm text-center">
              Your card was declined. No charges were made. Please check your card details and try again.
            </p>
          </div>
        )}

        {/* Actions */}
        <div className="space-y-3 slide-up">
          {isSuccess ? (
            <>
              <Link to="/bookings" className="btn-primary w-full py-4 rounded-xl flex items-center justify-center gap-2">
                <Ticket className="w-5 h-5" /> View My Bookings
              </Link>
              <Link to="/events" className="btn-secondary w-full py-4 rounded-xl flex items-center justify-center gap-2">
                <Home className="w-5 h-5" /> Browse More Events
              </Link>
            </>
          ) : (
            <>
              <button onClick={() => navigate(-1)} className="btn-primary w-full py-4 rounded-xl flex items-center justify-center gap-2">
                <ArrowRight className="w-5 h-5" /> Try Again
              </button>
              <Link to="/events" className="btn-secondary w-full py-4 rounded-xl flex items-center justify-center gap-2">
                <Home className="w-5 h-5" /> Back to Events
              </Link>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
