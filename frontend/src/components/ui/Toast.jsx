import React from 'react';
import { CheckCircle, XCircle, Info, X } from 'lucide-react';
import { useApp } from '../../context/AppContext';

const ICONS = {
  success: <CheckCircle size={20} />,
  error: <XCircle size={20} />,
  info: <Info size={20} />,
  warning: <Info size={20} />,
};

const COLORS = {
  success: { bg: 'rgba(16,185,129,0.12)', border: 'rgba(16,185,129,0.3)', color: '#34d399' },
  error:   { bg: 'rgba(239,68,68,0.12)',  border: 'rgba(239,68,68,0.3)',  color: '#f87171' },
  info:    { bg: 'rgba(56,189,248,0.12)', border: 'rgba(56,189,248,0.3)', color: '#7dd3fc' },
  warning: { bg: 'rgba(245,158,11,0.12)', border: 'rgba(245,158,11,0.3)', color: '#fbbf24' },
};

const Toast = () => {
  const { toast } = useApp();
  if (!toast.show) return null;

  const c = COLORS[toast.type] || COLORS.success;

  return (
    <div style={{
      position: 'fixed',
      bottom: '2rem',
      right: '2rem',
      background: c.bg,
      backdropFilter: 'blur(20px)',
      border: `1px solid ${c.border}`,
      borderRadius: '14px',
      padding: '1rem 1.5rem',
      display: 'flex',
      alignItems: 'center',
      gap: '0.75rem',
      maxWidth: '360px',
      zIndex: 9999,
      boxShadow: '0 20px 60px rgba(0,0,0,0.4)',
      animation: 'slideUpFade 0.4s cubic-bezier(0.16,1,0.3,1) forwards',
    }}>
      <span style={{ color: c.color, flexShrink: 0 }}>{ICONS[toast.type]}</span>
      <span style={{ fontSize: '0.9rem', fontWeight: '500', color: 'var(--text-main)', lineHeight: 1.4 }}>
        {toast.message}
      </span>
    </div>
  );
};

export default Toast;
