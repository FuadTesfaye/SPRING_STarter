import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Shield, Mail, Lock, Eye, EyeOff, AlertCircle } from 'lucide-react';

const ADMIN_EMAIL = 'philemondan32@gmail.com';
const ADMIN_PASSWORD = 'Dantab@4040';

export default function AdminLoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPw, setShowPw] = useState(false);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    await new Promise(r => setTimeout(r, 600));

    if (email.trim().toLowerCase() === ADMIN_EMAIL && password === ADMIN_PASSWORD) {
      sessionStorage.setItem('adminAuth', 'true');
      navigate('/admin');
    } else {
      setError('Invalid admin credentials.');
    }
    setLoading(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-[#0a0a14]">
      <div className="w-full max-w-md slide-up">
        <div className="text-center mb-8">
          <div className="w-16 h-16 rounded-2xl bg-gradient-to-br from-purple-600 to-pink-600 flex items-center justify-center mx-auto mb-4 shadow-lg shadow-purple-500/30">
            <Shield className="w-8 h-8 text-white" />
          </div>
          <h1 className="text-3xl font-black text-white">Admin Access</h1>
          <p className="text-white/40 mt-2 text-sm">TicketHub Administration Panel</p>
        </div>

        <div className="glass-strong p-8 rounded-2xl">
          {error && (
            <div className="flex items-center gap-2 p-3 rounded-xl bg-red-500/10 border border-red-500/20 mb-6 fade-in">
              <AlertCircle className="w-4 h-4 text-red-400 shrink-0" />
              <span className="text-sm text-red-300">{error}</span>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="text-sm text-white/60 mb-2 block">Admin Email</label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
                <input
                  type="email"
                  placeholder="admin@example.com"
                  value={email}
                  onChange={e => setEmail(e.target.value)}
                  className="input-field pl-10"
                  required
                  autoComplete="email"
                />
              </div>
            </div>

            <div>
              <label className="text-sm text-white/60 mb-2 block">Password</label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-white/30" />
                <input
                  type={showPw ? 'text' : 'password'}
                  placeholder="••••••••"
                  value={password}
                  onChange={e => setPassword(e.target.value)}
                  className="input-field pl-10 pr-10"
                  required
                />
                <button type="button" onClick={() => setShowPw(!showPw)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-white/30 hover:text-white/60">
                  {showPw ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                </button>
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="btn-primary w-full py-4 rounded-xl text-base flex items-center justify-center gap-2 mt-2"
            >
              {loading
                ? <div className="w-5 h-5 rounded-full border-2 border-white/30 border-t-white animate-spin" />
                : <><Shield className="w-5 h-5" /> Sign In to Admin</>}
            </button>
          </form>

          <p className="text-center text-xs text-white/20 mt-6">
            Restricted access. Unauthorized attempts are logged.
          </p>
        </div>

        <div className="text-center mt-4">
          <a href="/" className="text-white/30 text-sm hover:text-white/60 transition-colors">
            ← Back to TicketHub
          </a>
        </div>
      </div>
    </div>
  );
}
