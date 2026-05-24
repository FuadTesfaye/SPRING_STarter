import React, { useEffect, useState } from 'react';
import Sidebar from '../../components/admin/Sidebar';
import { ShoppingBag, Eye, X, Check, Truck, CreditCard, ShieldCheck } from 'lucide-react';
import { api } from '../../services/api';

const OrderManagement = () => {
  const [orders, setOrders] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [filterStatus, setFilterStatus] = useState('ALL');
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [modalDetails, setModalDetails] = useState({ payment: null, shipment: null });
  const [modalLoading, setModalLoading] = useState(false);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setIsLoading(true);
    try {
      const data = await api.getOrders();
      setOrders(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error('Failed to fetch admin orders:', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleOpenModal = async (order) => {
    setSelectedOrder(order);
    setModalLoading(true);
    try {
      const [payment, shipment] = await Promise.all([
        api.getPaymentByOrderId(order.orderId).catch(() => null),
        api.getShipmentByOrderId(order.orderId).catch(() => null)
      ]);
      setModalDetails({ payment, shipment });
    } catch (err) {
      console.error('Failed to load modal order details:', err);
    } finally {
      setModalLoading(false);
    }
  };

  const handleCloseModal = () => {
    setSelectedOrder(null);
    setModalDetails({ payment: null, shipment: null });
  };

  const filteredOrders = orders.filter(o => {
    if (filterStatus === 'ALL') return true;
    return o.status === filterStatus;
  });

  return (
    <div style={{ display: 'flex', minHeight: '100vh', background: 'var(--bg-dark)' }}>
      <Sidebar />

      <main style={{ flex: 1, marginLeft: '280px', padding: '3rem' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '3rem' }}>
          <div>
            <h1 style={{ fontSize: '2.2rem', fontWeight: '800', marginBottom: '0.5rem' }}>Order Management</h1>
            <p style={{ color: 'var(--text-muted)' }}>Fulfill orders, review invoices, and verify deliveries.</p>
          </div>
        </div>

        {/* Filter Toolbar tabs */}
        <div style={{ display: 'flex', gap: '0.5rem', background: 'var(--bg-card)', padding: '0.4rem', borderRadius: '10px', border: '1px solid var(--border)', marginBottom: '2rem', width: 'fit-content' }}>
          {['ALL', 'CREATED', 'SHIPPED', 'DELIVERED'].map(st => (
            <button
              key={st}
              onClick={() => setFilterStatus(st)}
              style={{
                padding: '0.5rem 1.25rem', borderRadius: '8px', fontSize: '0.82rem', fontWeight: '700', cursor: 'pointer',
                background: filterStatus === st ? 'var(--primary)' : 'none',
                color: filterStatus === st ? 'white' : 'var(--text-muted)',
                transition: 'var(--transition)'
              }}
            >
              {st}
            </button>
          ))}
        </div>

        {/* Orders Table list */}
        <div className="card" style={{ padding: '2rem' }}>
          {isLoading ? (
            <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem 0' }}>Loading system orders...</p>
          ) : filteredOrders.length === 0 ? (
            <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem 0' }}>No orders found matching status.</p>
          ) : (
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)', color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                  <th style={{ padding: '1rem 0' }}>Order ID</th>
                  <th>Customer ID</th>
                  <th>Total Amount</th>
                  <th>Status</th>
                  <th style={{ textAlign: 'right' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredOrders.map(order => (
                  <tr key={order.orderId} style={{ borderBottom: '1px solid var(--border)', fontSize: '0.9rem' }}>
                    <td style={{ padding: '1rem 0', fontWeight: '700' }}>#{order.orderId.substring(0, 8).toUpperCase()}</td>
                    <td style={{ color: 'var(--text-muted)' }}>{order.customerId.substring(0, 20)}...</td>
                    <td style={{ fontWeight: '700' }}>${order.totalAmount.toFixed(2)}</td>
                    <td>
                      <span className={`pill ${
                        order.status === 'DELIVERED' ? 'pill-success' :
                        order.status === 'SHIPPED' ? 'pill-info' : 'pill-warning'
                      }`} style={{ fontSize: '0.75rem' }}>
                        {order.status}
                      </span>
                    </td>
                    <td style={{ textAlign: 'right' }}>
                      <button
                        onClick={() => handleOpenModal(order)}
                        className="btn-ghost"
                        style={{ padding: '0.4rem', color: 'var(--primary)' }}
                      >
                        <Eye size={16} /> Details
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </main>

      {/* Order Details Modal popup */}
      {selectedOrder && (
        <div style={{ position: 'fixed', inset: 0, zIndex: 3000, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <div onClick={handleCloseModal} style={{ position: 'absolute', inset: 0, background: 'rgba(0,0,0,0.7)', backdropFilter: 'blur(5px)' }} />

          <div className="card animate-scale" style={{ width: '560px', zIndex: 1, padding: '2.5rem', background: 'var(--bg-dark)', display: 'flex', flexDirection: 'column', gap: '1.5rem', maxHeight: '90vh', overflowY: 'auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3 style={{ fontSize: '1.25rem', fontWeight: '800' }}>Order Details</h3>
              <button onClick={handleCloseModal} style={{ background: 'var(--bg-elevated)', border: '1px solid var(--border)', padding: '0.4rem', borderRadius: '8px', color: 'var(--text-muted)', cursor: 'pointer', display: 'flex' }}>
                <X size={18} />
              </button>
            </div>

            <div>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>ORDER UUID:</span>
              <p style={{ fontWeight: '700', fontSize: '0.9rem' }}>{selectedOrder.orderId}</p>
            </div>

            <hr style={{ border: 'none', borderTop: '1px solid var(--border)' }} />

            {/* Line items row */}
            <div>
              <h4 style={{ fontSize: '0.9rem', fontWeight: '700', marginBottom: '0.75rem', color: 'var(--primary)' }}>Items Ordered</h4>
              <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', background: 'var(--bg-elevated)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--border)' }}>
                {Array.isArray(selectedOrder.items) && selectedOrder.items.length > 0 ? (
                  selectedOrder.items.map((it, idx) => (
                    <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.82rem' }}>
                      <span>{it.quantity}x {it.productName}</span>
                      <strong style={{ color: 'var(--text-main)' }}>${(it.price * it.quantity).toFixed(2)}</strong>
                    </div>
                  ))
                ) : (
                  <p style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>No line items recorded.</p>
                )}
                <div style={{ display: 'flex', justifyContent: 'space-between', borderTop: '1px solid var(--border)', paddingTop: '0.5rem', marginTop: '0.5rem', fontSize: '0.9rem', fontWeight: '700' }}>
                  <span>Total Invoice:</span>
                  <span style={{ color: 'var(--primary)' }}>${selectedOrder.totalAmount.toFixed(2)}</span>
                </div>
              </div>
            </div>

            {/* Status updates display */}
            {modalLoading ? (
              <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', textAlign: 'center' }}>Connecting to payment and tracking APIs...</p>
            ) : (
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                <div style={{ background: 'var(--bg-elevated)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--border)' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', color: 'var(--success)', fontWeight: '700', fontSize: '0.8rem', marginBottom: '0.4rem' }}>
                    <CreditCard size={14} /> PAYMENT DETAILS
                  </div>
                  {modalDetails.payment ? (
                    <>
                      <p style={{ fontSize: '0.85rem', fontWeight: '700' }}>Invoice: #{modalDetails.payment.id?.substring(0, 8).toUpperCase()}</p>
                      <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Status: {modalDetails.payment.status}</span>
                    </>
                  ) : (
                    <p style={{ fontSize: '0.85rem', color: 'var(--warning)' }}>No payment details active</p>
                  )}
                </div>

                <div style={{ background: 'var(--bg-elevated)', padding: '1rem', borderRadius: '8px', border: '1px solid var(--border)' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', color: 'var(--primary)', fontWeight: '700', fontSize: '0.8rem', marginBottom: '0.4rem' }}>
                    <Truck size={14} /> TRACKING PARCEL
                  </div>
                  {modalDetails.shipment ? (
                    <>
                      <p style={{ fontSize: '0.85rem', fontWeight: '700' }}>#{modalDetails.shipment.trackingNumber}</p>
                      <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Delivery address logged</span>
                    </>
                  ) : (
                    <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Preparing tracking number...</p>
                  )}
                </div>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default OrderManagement;
