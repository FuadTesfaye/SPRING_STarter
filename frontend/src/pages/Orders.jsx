import React, { useEffect, useState } from 'react';
import { Package, CheckCircle, Clock, CreditCard, Truck, ChevronRight, ChevronDown, RefreshCw, Calendar, MapPin } from 'lucide-react';
import { api } from '../services/api';
import { useApp } from '../context/AppContext';
import { Link } from 'react-router-dom';

const Orders = () => {
  const { addToCart, showToast } = useApp();
  const [orders, setOrders] = useState([]);
  const [details, setDetails] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('ALL'); // 'ALL' | 'ACTIVE' | 'FULFILLED'
  const [expandedOrderId, setExpandedOrderId] = useState(null);

  useEffect(() => {
    const fetchOrdersAndDetails = async () => {
      try {
        const data = await api.getOrders();
        const ordersList = Array.isArray(data) ? data : [];
        // Sort orders by most recent first
        ordersList.sort((a, b) => b.orderId.localeCompare(a.orderId));
        setOrders(ordersList);

        // Fetch details for each order in parallel
        const detailsPromises = ordersList.map(async (order) => {
          try {
            const [payment, shipment] = await Promise.all([
              api.getPaymentByOrderId(order.orderId),
              api.getShipmentByOrderId(order.orderId)
            ]);
            return { orderId: order.orderId, payment, shipment };
          } catch (err) {
            console.error(`Failed to fetch details for order ${order.orderId}`, err);
            return { orderId: order.orderId, payment: null, shipment: null };
          }
        });

        const detailsResults = await Promise.all(detailsPromises);
        const detailsMap = {};
        detailsResults.forEach((res) => {
          detailsMap[res.orderId] = { payment: res.payment, shipment: res.shipment };
        });
        setDetails(detailsMap);
      } catch (error) {
        console.error('Failed to fetch orders:', error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchOrdersAndDetails();
  }, []);

  const toggleExpandOrder = (orderId) => {
    setExpandedOrderId(expandedOrderId === orderId ? null : orderId);
  };

  const handleReorder = (order) => {
    if (Array.isArray(order.items)) {
      order.items.forEach(item => {
        addToCart({
          id: item.productId,
          name: item.productName || 'Quantum Device',
          price: item.price,
        }, item.quantity);
      });
      showToast('Items added to your cart!', 'success');
    } else {
      showToast('No items found in this order to copy.', 'warning');
    }
  };

  // Helper to determine stepper progress
  const getFulfillmentStep = (status) => {
    if (status === 'DELIVERED') return 3;
    if (status === 'SHIPPED') return 2;
    if (status === 'PROCESSING' || status === 'CONFIRMED') return 1;
    return 0; // 'CREATED' / 'PLACED'
  };

  // Tab Filtering
  const filteredOrders = orders.filter(order => {
    if (activeTab === 'ALL') return true;
    if (activeTab === 'ACTIVE') return order.status !== 'DELIVERED' && order.status !== 'CANCELLED';
    if (activeTab === 'FULFILLED') return order.status === 'DELIVERED';
    return true;
  });

  const renderTimeline = (status) => {
    const steps = ['Placed', 'Processing', 'Shipped', 'Delivered'];
    const currentStep = getFulfillmentStep(status);

    return (
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', margin: '1.5rem 0', position: 'relative' }}>
        {/* Background line */}
        <div style={{ position: 'absolute', height: '2px', background: 'var(--border)', left: '10px', right: '10px', top: '12px', zIndex: 0 }} />
        {/* Active line */}
        <div style={{
          position: 'absolute', height: '2px', background: 'var(--primary)', left: '10px',
          width: `${(currentStep / 3) * 95}%`, top: '12px', zIndex: 0, transition: 'var(--transition)'
        }} />

        {steps.map((label, idx) => {
          const isActive = currentStep >= idx;
          return (
            <div key={label} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', zIndex: 1 }}>
              <div style={{
                width: '26px', height: '26px', borderRadius: '50%',
                background: isActive ? 'var(--primary)' : 'var(--bg-elevated)',
                border: `2px solid ${isActive ? 'var(--primary)' : 'var(--border)'}`,
                display: 'flex', alignItems: 'center', justifyContent: 'center'
              }}>
                {isActive && <div style={{ width: '8px', height: '8px', borderRadius: '50%', background: 'white' }} />}
              </div>
              <span style={{ fontSize: '0.72rem', color: isActive ? 'var(--text-main)' : 'var(--text-muted)', fontWeight: isActive ? '700' : '500', marginTop: '0.4rem' }}>{label}</span>
            </div>
          );
        })}
      </div>
    );
  };

  if (isLoading) {
    return (
      <div className="container page-content" style={{ paddingTop: '150px', textAlign: 'center' }}>
        <p style={{ color: 'var(--text-muted)' }}>Loading your premium order timelines...</p>
      </div>
    );
  }

  return (
    <div className="container page-content" style={{ paddingTop: '120px' }}>
      <h2 style={{ fontSize: '2.5rem', fontWeight: '800', marginBottom: '2rem' }}>My Orders</h2>

      {/* Tabs */}
      <div style={{ display: 'flex', gap: '1rem', borderBottom: '1px solid var(--border)', paddingBottom: '1rem', marginBottom: '2.5rem' }}>
        {['ALL', 'ACTIVE', 'FULFILLED'].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            style={{
              background: 'none', border: 'none', padding: '0.5rem 1rem', fontSize: '0.9rem', fontWeight: '600', cursor: 'pointer',
              color: activeTab === tab ? 'var(--primary)' : 'var(--text-muted)',
              borderBottom: activeTab === tab ? '2px solid var(--primary)' : '2px solid transparent',
              transition: 'var(--transition)'
            }}
          >
            {tab.charAt(0) + tab.slice(1).toLowerCase()} Orders
          </button>
        ))}
      </div>

      {filteredOrders.length === 0 ? (
        <div className="card" style={{ padding: '4rem 2rem', textAlign: 'center' }}>
          <Package size={56} style={{ color: 'var(--text-subtle)', marginBottom: '1.5rem', opacity: 0.3 }} />
          <h3>No Orders Found</h3>
          <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '2rem' }}>You don't have any orders listed in this category.</p>
          <Link to="/shop" className="btn-primary">Browse Catalog</Link>
        </div>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          {filteredOrders.map((order) => {
            const orderDetail = details[order.orderId] || { payment: null, shipment: null };
            const payment = orderDetail.payment;
            const shipment = orderDetail.shipment;
            const isExpanded = expandedOrderId === order.orderId;

            return (
              <div key={order.orderId} className="card animate-fade" style={{ padding: '1.75rem', display: 'flex', flexDirection: 'column' }}>
                {/* Order Top Bar */}
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid var(--border)', paddingBottom: '1.25rem', flexWrap: 'wrap', gap: '1rem' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                    <div style={{ background: 'var(--primary-glow)', color: 'var(--primary)', padding: '0.65rem', borderRadius: '10px', display: 'flex' }}>
                      <Package size={20} />
                    </div>
                    <div>
                      <h4 style={{ fontSize: '1.05rem', fontWeight: '800' }}>Order #{order.orderId.substring(0, 8).toUpperCase()}</h4>
                      <p style={{ color: 'var(--text-muted)', fontSize: '0.78rem', marginTop: '0.2rem' }}>ID: {order.orderId}</p>
                    </div>
                  </div>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
                    <span style={{ fontSize: '1.2rem', fontWeight: '800', color: 'var(--primary)' }}>${order.totalAmount.toFixed(2)}</span>
                    <button
                      onClick={() => toggleExpandOrder(order.orderId)}
                      style={{ background: 'var(--bg-elevated)', border: '1px solid var(--border)', borderRadius: '8px', color: 'var(--text-muted)', cursor: 'pointer', padding: '0.4rem', display: 'flex' }}
                    >
                      <ChevronDown size={18} style={{ transform: isExpanded ? 'rotate(180deg)' : 'none', transition: 'var(--transition)' }} />
                    </button>
                  </div>
                </div>

                {/* Timeline display */}
                {renderTimeline(order.status)}

                {/* Details Section */}
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1.2fr', gap: '2.5rem', background: 'var(--bg-elevated)', padding: '1.25rem 1.5rem', borderRadius: 'var(--radius-md)', border: '1px solid var(--border)', marginTop: '1rem' }}>
                  {/* Fulfillment Status */}
                  <div>
                    <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', display: 'block', marginBottom: '0.35rem' }}>Fulfillment Status</span>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', fontWeight: '700', fontSize: '0.9rem' }}>
                      {order.status === 'DELIVERED' ? (
                        <><CheckCircle size={15} color="var(--success)" /> <span style={{ color: 'var(--success)' }}>Delivered</span></>
                      ) : order.status === 'SHIPPED' ? (
                        <><Truck size={15} color="var(--accent)" /> <span style={{ color: 'var(--accent)' }}>In Transit</span></>
                      ) : (
                        <><Clock size={15} color="var(--warning)" /> <span style={{ color: 'var(--warning)' }}>Processing</span></>
                      )}
                    </div>
                  </div>

                  {/* Payment Details */}
                  <div>
                    <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', display: 'block', marginBottom: '0.35rem' }}>Payment Status</span>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', fontWeight: '700', fontSize: '0.9rem' }}>
                      <CreditCard size={15} color={payment ? 'var(--success)' : 'var(--warning)'} />
                      <span style={{ color: payment ? 'var(--success)' : 'var(--warning)' }}>
                        {payment ? payment.status : 'PENDING'}
                      </span>
                    </div>
                  </div>

                  {/* Shipment Tracking details */}
                  <div>
                    <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', display: 'block', marginBottom: '0.35rem' }}>Shipping Details</span>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', fontWeight: '700', fontSize: '0.9rem' }}>
                      <Truck size={15} color="var(--primary)" />
                      <span style={{ color: 'var(--text-main)', fontSize: '0.85rem' }}>
                        {shipment ? `Tracking: ${shipment.trackingNumber}` : 'Preparing parcel tracking...'}
                      </span>
                    </div>
                  </div>
                </div>

                {/* Expanded Item Row Details */}
                {isExpanded && (
                  <div className="animate-fade" style={{ marginTop: '1.5rem', borderTop: '1px solid var(--border)', paddingTop: '1.5rem' }}>
                    <h5 style={{ fontSize: '0.9rem', fontWeight: '700', marginBottom: '1rem', color: 'var(--text-muted)' }}>Order Line Items</h5>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem', marginBottom: '1.5rem' }}>
                      {Array.isArray(order.items) && order.items.length > 0 ? (
                        order.items.map((item, idx) => (
                          <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem' }}>
                            <div>
                              <strong style={{ color: 'var(--primary)' }}>{item.quantity}x</strong> <span style={{ color: 'var(--text-main)' }}>{item.productName}</span>
                            </div>
                            <span style={{ fontWeight: '700' }}>${(item.price * item.quantity).toFixed(2)}</span>
                          </div>
                        ))
                      ) : (
                        <p style={{ fontSize: '0.8rem', color: 'var(--text-subtle)' }}>No detailed item rows recorded.</p>
                      )}
                    </div>

                    <div style={{ display: 'flex', gap: '1rem', justifyContent: 'flex-end' }}>
                      <button
                        onClick={() => handleReorder(order)}
                        className="btn-secondary"
                        style={{ display: 'inline-flex', alignItems: 'center', gap: '0.4rem', padding: '0.5rem 1.25rem', fontSize: '0.8rem' }}
                      >
                        <RefreshCw size={14} /> Buy Again / Reorder
                      </button>
                    </div>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Orders;
