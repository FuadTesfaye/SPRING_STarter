import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { User, Mail, Shield, ShieldCheck, MapPin, Key, LogOut, Calendar, Plus, Trash2 } from 'lucide-react';
import { useApp } from '../context/AppContext';

const Profile = () => {
  const { user, logout, showToast } = useApp();
  const navigate = useNavigate();

  const [passwordData, setPasswordData] = useState({ currentPassword: '', newPassword: '', confirmPassword: '' });
  const [addresses, setAddresses] = useState([
    { id: 1, label: 'Home Address', street: '123 Tech Avenue', city: 'Silicon Valley', zip: '94025', primary: true },
    { id: 2, label: 'Work Address', street: '500 Innovation Boulevard', city: 'San Francisco', zip: '94107', primary: false }
  ]);

  const [newAddr, setNewAddr] = useState({ label: '', street: '', city: '', zip: '' });
  const [showAddAddr, setShowAddAddr] = useState(false);

  const handlePasswordChange = (e) => {
    e.preventDefault();
    if (passwordData.newPassword !== passwordData.confirmPassword) {
      showToast('New passwords do not match!', 'error');
      return;
    }
    showToast('Password updated successfully! (Mocked)', 'success');
    setPasswordData({ currentPassword: '', newPassword: '', confirmPassword: '' });
  };

  const handleAddAddress = (e) => {
    e.preventDefault();
    if (!newAddr.label || !newAddr.street || !newAddr.city || !newAddr.zip) return;
    setAddresses([...addresses, { ...newAddr, id: Date.now(), primary: false }]);
    setNewAddr({ label: '', street: '', city: '', zip: '' });
    setShowAddAddr(false);
    showToast('Shipping address added!', 'success');
  };

  const handleDeleteAddress = (id) => {
    setAddresses(addresses.filter(a => a.id !== id));
    showToast('Address deleted', 'info');
  };

  const handleSetPrimaryAddress = (id) => {
    setAddresses(addresses.map(a => ({ ...a, primary: a.id === id })));
    showToast('Primary address set', 'success');
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!user) {
    return (
      <div className="container page-content" style={{ paddingTop: '150px', textAlign: 'center' }}>
        <div className="card" style={{ maxWidth: '450px', margin: '0 auto', padding: '3rem' }}>
          <User size={48} style={{ margin: '0 auto 1rem', color: 'var(--text-muted)' }} />
          <h3>Access Denied</h3>
          <p style={{ color: 'var(--text-muted)', margin: '1rem 0 2rem' }}>Please log in to view your profile dashboard.</p>
          <button onClick={() => navigate('/login')} className="btn-primary" style={{ width: '100%' }}>Log In Now</button>
        </div>
      </div>
    );
  }

  const initial = (user.username || 'U')[0].toUpperCase();

  return (
    <div className="container page-content" style={{ paddingTop: '120px' }}>
      <h2 style={{ fontSize: '2.5rem', fontWeight: '800', marginBottom: '2.5rem' }}>My Account</h2>

      <div style={{ display: 'grid', gridTemplateColumns: '1.2fr 2fr', gap: '3rem' }}>
        {/* Left Side: Avatar and Quick Info */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
          <div className="card animate-fade" style={{ padding: '2.5rem', textAlign: 'center' }}>
            <div style={{
              width: '90px', height: '90px', borderRadius: '50%',
              background: 'var(--gradient-primary)', margin: '0 auto 1.5rem',
              display: 'flex', alignItems: 'center', justifyContent: 'center',
              fontSize: '2.5rem', fontWeight: '800', color: 'white',
              boxShadow: 'var(--shadow-primary)'
            }}>
              {initial}
            </div>
            <h3 style={{ fontSize: '1.4rem', fontWeight: '700', marginBottom: '0.25rem' }}>{user.username}</h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem', marginBottom: '1.5rem' }}>{user.email || 'brukdagim@gmail.com'}</p>
            <div style={{ display: 'inline-flex', alignItems: 'center', gap: '0.4rem', background: 'var(--success-bg)', color: 'var(--success)', padding: '0.3rem 0.8rem', borderRadius: 'var(--radius-full)', fontSize: '0.78rem', fontWeight: '600' }}>
              <ShieldCheck size={14} /> Verified Account
            </div>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem', borderTop: '1px solid var(--border)', marginTop: '2rem', paddingTop: '1.5rem', textAlign: 'left' }}>
              <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center', color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                <Calendar size={16} /> <span>Member since: May 2026</span>
              </div>
              <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center', color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                <Shield size={16} /> <span>ID: {user.userId?.substring(0, 18) || '0000-0000-0000'}</span>
              </div>
            </div>

            <button onClick={handleLogout} className="btn-secondary" style={{ width: '100%', marginTop: '2rem', borderColor: 'var(--error)', color: 'var(--error)' }}>
              <LogOut size={16} /> Log Out
            </button>
          </div>
        </div>

        {/* Right Side: Settings Tabs & Forms */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
          {/* Addresses Card */}
          <div className="card animate-fade" style={{ padding: '2rem' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
              <h3 style={{ fontSize: '1.2rem', fontWeight: '800', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                <MapPin size={20} color="var(--primary)" /> Saved Addresses
              </h3>
              <button onClick={() => setShowAddAddr(!showAddAddr)} className="btn-ghost" style={{ fontSize: '0.8rem', color: 'var(--primary)' }}>
                <Plus size={14} /> Add Address
              </button>
            </div>

            {showAddAddr && (
              <form onSubmit={handleAddAddress} style={{ background: 'var(--bg-elevated)', border: '1px solid var(--border)', borderRadius: 'var(--radius-md)', padding: '1.5rem', marginBottom: '1.5rem', display: 'flex', flexDirection: 'column', gap: '1rem' }} className="animate-fade">
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                  <input type="text" placeholder="Address Label (e.g. Office)" required value={newAddr.label} onChange={e => setNewAddr({...newAddr, label: e.target.value})} className="input" />
                  <input type="text" placeholder="Street Address" required value={newAddr.street} onChange={e => setNewAddr({...newAddr, street: e.target.value})} className="input" />
                  <input type="text" placeholder="City" required value={newAddr.city} onChange={e => setNewAddr({...newAddr, city: e.target.value})} className="input" />
                  <input type="text" placeholder="Zip Code" required value={newAddr.zip} onChange={e => setNewAddr({...newAddr, zip: e.target.value})} className="input" />
                </div>
                <div style={{ display: 'flex', gap: '0.75rem', justifyContent: 'flex-end' }}>
                  <button type="button" onClick={() => setShowAddAddr(false)} className="btn-secondary" style={{ padding: '0.5rem 1rem', fontSize: '0.8rem' }}>Cancel</button>
                  <button type="submit" className="btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.8rem' }}>Save Address</button>
                </div>
              </form>
            )}

            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
              {addresses.map(addr => (
                <div key={addr.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '1rem 1.25rem', background: 'var(--bg-elevated)', border: addr.primary ? '1px solid var(--primary)' : '1px solid var(--border)', borderRadius: 'var(--radius-md)' }}>
                  <div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.25rem' }}>
                      <span style={{ fontWeight: '700', fontSize: '0.9rem' }}>{addr.label}</span>
                      {addr.primary && <span className="pill pill-primary" style={{ fontSize: '0.65rem', padding: '1px 6px' }}>Default</span>}
                    </div>
                    <p style={{ color: 'var(--text-muted)', fontSize: '0.82rem' }}>{addr.street}, {addr.city} ({addr.zip})</p>
                  </div>
                  <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center' }}>
                    {!addr.primary && (
                      <button onClick={() => handleSetPrimaryAddress(addr.id)} style={{ background: 'none', color: 'var(--text-muted)', fontSize: '0.78rem', cursor: 'pointer' }}
                        onMouseEnter={e => e.currentTarget.style.color = 'var(--primary)'}
                        onMouseLeave={e => e.currentTarget.style.color = 'var(--text-muted)'}
                      >Set Default</button>
                    )}
                    <button onClick={() => handleDeleteAddress(addr.id)} style={{ background: 'none', color: 'var(--text-muted)', cursor: 'pointer' }}
                      onMouseEnter={e => e.currentTarget.style.color = 'var(--error)'}
                      onMouseLeave={e => e.currentTarget.style.color = 'var(--text-muted)'}
                    >
                      <Trash2 size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Change Password Card */}
          <div className="card animate-fade" style={{ padding: '2rem' }}>
            <h3 style={{ fontSize: '1.2rem', fontWeight: '800', display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1.5rem' }}>
              <Key size={20} color="var(--primary)" /> Change Password
            </h3>
            <form onSubmit={handlePasswordChange} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
              <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.85rem', color: 'var(--text-muted)' }}>Current Password</label>
                <input type="password" required value={passwordData.currentPassword} onChange={e => setPasswordData({...passwordData, currentPassword: e.target.value})} className="input" placeholder="••••••••" />
              </div>
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.25rem' }}>
                <div>
                  <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.85rem', color: 'var(--text-muted)' }}>New Password</label>
                  <input type="password" required value={passwordData.newPassword} onChange={e => setPasswordData({...passwordData, newPassword: e.target.value})} className="input" placeholder="••••••••" />
                </div>
                <div>
                  <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.85rem', color: 'var(--text-muted)' }}>Confirm New Password</label>
                  <input type="password" required value={passwordData.confirmPassword} onChange={e => setPasswordData({...passwordData, confirmPassword: e.target.value})} className="input" placeholder="••••••••" />
                </div>
              </div>
              <button type="submit" className="btn-primary" style={{ width: 'fit-content', alignSelf: 'flex-end', marginTop: '0.5rem' }}>
                Update Password
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
