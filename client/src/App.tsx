import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useAuthStore } from './store';
import Header from './components/Header';
import HomePage from './pages/HomePage';
import EventsPage from './pages/EventsPage';
import EventDetailPage from './pages/EventDetailPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import BookingHistoryPage from './pages/BookingHistoryPage';
import PaymentResultPage from './pages/PaymentResultPage';
import AdminLoginPage from './pages/AdminLoginPage';
import AdminPage from './pages/AdminPage';
import ShopPage from './pages/ShopPage';

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { user } = useAuthStore();
  if (!user) return <Navigate to="/login" replace />;
  return <>{children}</>;
}

function PublicRoute({ children }: { children: React.ReactNode }) {
  const { user } = useAuthStore();
  if (user) return <Navigate to="/events" replace />;
  return <>{children}</>;
}

function AppWrapper() {
  const { initializeAuth } = useAuthStore();
  useEffect(() => { initializeAuth(); }, [initializeAuth]);
  return (
    <Router>
      <div className="min-h-screen bg-grid">
        <div className="fixed inset-0 pointer-events-none overflow-hidden">
          <div className="absolute -top-40 -right-40 w-96 h-96 bg-purple-600 rounded-full mix-blend-multiply filter blur-3xl opacity-10 floating" />
          <div className="absolute -bottom-40 -left-40 w-96 h-96 bg-blue-600 rounded-full mix-blend-multiply filter blur-3xl opacity-10 floating" style={{ animationDelay: '2s' }} />
          <div className="absolute top-1/2 left-1/2 -translate-x-1/2 w-96 h-96 bg-pink-600 rounded-full mix-blend-multiply filter blur-3xl opacity-5 floating" style={{ animationDelay: '4s' }} />
        </div>
        <Routes>
          <Route path="/login" element={<PublicRoute><LoginPage /></PublicRoute>} />
          <Route path="/register" element={<PublicRoute><RegisterPage /></PublicRoute>} />

          <Route path="/" element={<><Header /><HomePage /></>} />
          <Route path="/events" element={<><Header /><EventsPage /></>} />
          <Route path="/events/:id" element={<><Header /><EventDetailPage /></>} />

          <Route path="/dashboard" element={<ProtectedRoute><Header /><DashboardPage /></ProtectedRoute>} />
          <Route path="/bookings" element={<ProtectedRoute><Header /><BookingHistoryPage /></ProtectedRoute>} />
          <Route path="/payment/result" element={<ProtectedRoute><Header /><PaymentResultPage /></ProtectedRoute>} />

          <Route path="/shop" element={<><Header /><ShopPage /></>} />

          <Route path="/admin/login" element={<AdminLoginPage />} />
          <Route path="/admin" element={<AdminPage />} />

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default AppWrapper;
