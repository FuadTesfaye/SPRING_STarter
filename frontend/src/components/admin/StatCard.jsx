import React from 'react';

const StatCard = ({ label, value, icon: Icon, trend }) => {
  return (
    <div className="card" style={{ padding: '1.5rem', display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
      <div style={{ width: '56px', height: '56px', background: 'rgba(99, 102, 241, 0.1)', borderRadius: '14px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <Icon size={28} color="var(--primary)" />
      </div>
      <div>
        <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '0.25rem' }}>{label}</p>
        <div style={{ display: 'flex', alignItems: 'baseline', gap: '0.5rem' }}>
          <h3 style={{ fontSize: '1.5rem', fontWeight: '700' }}>{value}</h3>
          {trend && <span style={{ fontSize: '0.8rem', color: trend.startsWith('+') ? 'var(--success)' : 'var(--error)' }}>{trend}</span>}
        </div>
      </div>
    </div>
  );
};

export default StatCard;
