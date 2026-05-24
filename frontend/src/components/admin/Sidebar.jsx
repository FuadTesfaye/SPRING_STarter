import React from 'react';
import { LayoutDashboard, Package, ShoppingCart, Users, Settings, ArrowLeft } from 'lucide-react';
import { Link, useLocation } from 'react-router-dom';

const Sidebar = () => {
  const location = useLocation();

  const menuItems = [
    { icon: LayoutDashboard, label: 'Dashboard', path: '/admin' },
    { icon: Package, label: 'Products', path: '/admin/products' },
    { icon: Package, label: 'Inventory', path: '/admin/inventory' },
    { icon: ShoppingCart, label: 'Orders', path: '/admin/orders' },
    { icon: Users, label: 'Customers', path: '/admin/customers' },
    { icon: Settings, label: 'Settings', path: '/admin/settings' },
  ];

  return (
    <div style={{ 
      width: '280px', 
      background: 'var(--bg-card)', 
      borderRight: '1px solid var(--border)', 
      height: '100vh', 
      position: 'fixed', 
      left: 0, 
      top: 0, 
      padding: '2rem 1.5rem',
      display: 'flex',
      flexDirection: 'column'
    }}>
      <div style={{ marginBottom: '3rem', display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
        <div style={{ width: '40px', height: '40px', background: 'var(--primary)', borderRadius: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <Package color="white" size={24} />
        </div>
        <span style={{ fontSize: '1.2rem', fontWeight: '700', letterSpacing: '-0.5px' }}>Admin Panel</span>
      </div>

      <nav style={{ flex: 1 }}>
        {menuItems.map((item) => {
          const isActive = location.pathname === item.path;
          return (
            <Link 
              key={item.label}
              to={item.path}
              style={{ 
                display: 'flex', 
                alignItems: 'center', 
                gap: '1rem', 
                padding: '0.85rem 1rem', 
                borderRadius: '12px', 
                textDecoration: 'none', 
                color: isActive ? 'var(--primary)' : 'var(--text-muted)',
                background: isActive ? 'rgba(99, 102, 241, 0.1)' : 'transparent',
                marginBottom: '0.5rem',
                transition: 'all 0.2s ease'
              }}
            >
              <item.icon size={20} />
              <span style={{ fontWeight: isActive ? '600' : '400' }}>{item.label}</span>
            </Link>
          );
        })}
      </nav>

      <Link 
        to="/" 
        style={{ 
          display: 'flex', 
          alignItems: 'center', 
          gap: '1rem', 
          padding: '1rem', 
          color: 'var(--text-muted)', 
          textDecoration: 'none',
          borderTop: '1px solid var(--border)',
          marginTop: '2rem'
        }}
      >
        <ArrowLeft size={18} />
        Back to Store
      </Link>
    </div>
  );
};

export default Sidebar;
