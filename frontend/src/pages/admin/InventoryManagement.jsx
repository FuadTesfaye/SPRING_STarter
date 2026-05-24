import React, { useState, useEffect } from 'react';
import Sidebar from '../../components/admin/Sidebar';
import { Package, Search, RefreshCw, AlertTriangle, CheckCircle, Database } from 'lucide-react';
import { api } from '../../services/api';

const InventoryManagement = () => {
  const [products, setProducts] = useState([]);
  const [stocks, setStocks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [filterText, setFilterText] = useState('');
  const [replenishAmounts, setReplenishAmounts] = useState({});

  useEffect(() => {
    fetchInventoryData();
  }, []);

  const fetchInventoryData = async () => {
    setIsLoading(true);
    try {
      const productList = await api.getProducts();
      setProducts(productList);

      const stockList = await api.getInventory();
      setStocks(stockList);
    } catch (err) {
      console.error('Failed to load inventory data', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleUpdateStock = async (product, amount) => {
    const qty = parseInt(amount, 10);
    if (isNaN(qty) || qty < 0) {
      alert('Please enter a valid stock quantity');
      return;
    }

    try {
      await api.updateInventory({
        productId: product.id,
        productName: product.name,
        quantity: qty
      });
      alert(`Successfully updated stock for "${product.name}" to ${qty} units!`);
      
      // Reset replenish input
      setReplenishAmounts(prev => ({ ...prev, [product.id]: '' }));
      
      // Reload lists
      fetchInventoryData();
    } catch (err) {
      console.error('Failed to update stock', err);
      alert('Failed to save stock update to inventory database.');
    }
  };

  // Match stock level for a product
  const getStockForProduct = (productId) => {
    return stocks.find(s => s.productId === productId);
  };

  // Metrics
  const lowStockCount = products.filter(p => {
    const s = getStockForProduct(p.id);
    return s && s.quantity > 0 && s.quantity < 10;
  }).length;

  const outOfStockCount = products.filter(p => {
    const s = getStockForProduct(p.id);
    return !s || s.quantity === 0;
  }).length;

  const filteredProducts = products.filter(p => 
    p.name.toLowerCase().includes(filterText.toLowerCase()) || 
    p.category.toLowerCase().includes(filterText.toLowerCase())
  );

  return (
    <div style={{ display: 'flex', minHeight: '100vh', background: 'var(--bg-dark)' }}>
      <Sidebar />
      
      <main style={{ flex: 1, marginLeft: '280px', padding: '3rem' }}>
        
        {/* Header */}
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '3rem' }}>
          <div>
            <h1 style={{ fontSize: '2rem', fontWeight: '700', marginBottom: '0.5rem' }}>Live Stock & Inventory</h1>
            <p style={{ color: 'var(--text-muted)' }}>Real-time database integration with inventory-service.</p>
          </div>
          <button 
            className="btn-primary" 
            onClick={fetchInventoryData}
            style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', padding: '0.75rem 1.25rem' }}
          >
            <RefreshCw size={18} /> Refresh Inventory
          </button>
        </div>

        {/* Dashboard Grid Cards */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '1.5rem', marginBottom: '3rem' }}>
          <div style={statCardStyle}>
            <Database size={24} color="var(--primary)" />
            <div style={{ marginTop: '1rem' }}>
              <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>Total Catalog Products</div>
              <div style={{ fontSize: '1.8rem', fontWeight: '700', marginTop: '0.25rem' }}>{products.length}</div>
            </div>
          </div>
          
          <div style={statCardStyle}>
            <CheckCircle size={24} color="var(--success)" />
            <div style={{ marginTop: '1rem' }}>
              <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>In Stock Levels</div>
              <div style={{ fontSize: '1.8rem', fontWeight: '700', marginTop: '0.25rem' }}>{products.length - outOfStockCount}</div>
            </div>
          </div>

          <div style={statCardStyle}>
            <AlertTriangle size={24} color="#f59e0b" />
            <div style={{ marginTop: '1rem' }}>
              <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>Low Stock Warnings (&lt;10)</div>
              <div style={{ fontSize: '1.8rem', fontWeight: '700', color: lowStockCount > 0 ? '#f59e0b' : 'white', marginTop: '0.25rem' }}>{lowStockCount}</div>
            </div>
          </div>

          <div style={statCardStyle}>
            <AlertTriangle size={24} color="var(--error)" />
            <div style={{ marginTop: '1rem' }}>
              <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>Out of Stock Alert</div>
              <div style={{ fontSize: '1.8rem', fontWeight: '700', color: outOfStockCount > 0 ? 'var(--error)' : 'white', marginTop: '0.25rem' }}>{outOfStockCount}</div>
            </div>
          </div>
        </div>

        {/* Main Content Area */}
        <div style={{ background: 'var(--bg-card)', borderRadius: '16px', border: '1px solid var(--border)', padding: '2rem' }}>
          
          {/* Search bar */}
          <div style={{ marginBottom: '2rem', display: 'flex', gap: '1rem' }}>
             <div style={{ position: 'relative', flex: 1 }}>
                <Search size={18} style={{ position: 'absolute', left: '1rem', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                <input 
                  type="text" 
                  value={filterText}
                  onChange={(e) => setFilterText(e.target.value)}
                  placeholder="Filter stock by name or category..." 
                  style={{ width: '100%', background: 'rgba(255,255,255,0.05)', border: '1px solid var(--border)', borderRadius: '10px', padding: '0.75rem 1rem 0.75rem 3rem', color: 'white', outline: 'none' }}
                />
             </div>
          </div>

          {/* Table */}
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)', color: 'var(--text-muted)', fontSize: '0.9rem' }}>
                <th style={{ padding: '1rem 0' }}>Product Details</th>
                <th>Category</th>
                <th>Unit Price</th>
                <th>Live Stock Status</th>
                <th style={{ width: '260px', textAlign: 'right' }}>Replenish Stock</th>
              </tr>
            </thead>
            <tbody>
              {filteredProducts.length === 0 ? (
                <tr>
                  <td colSpan="5" style={{ textAlign: 'center', padding: '4rem 0', color: 'var(--text-muted)' }}>
                    {isLoading ? 'Loading stock catalog...' : 'No inventory data found.'}
                  </td>
                </tr>
              ) : (
                filteredProducts.map((product) => {
                  const stock = getStockForProduct(product.id);
                  const isInitialized = !!stock;
                  const qty = isInitialized ? stock.quantity : 0;

                  let statusText = 'Not Initialized';
                  let statusBg = 'rgba(168, 85, 247, 0.15)';
                  let statusColor = '#c084fc';

                  if (isInitialized) {
                    if (qty === 0) {
                      statusText = 'Out of Stock';
                      statusBg = 'rgba(239, 68, 68, 0.15)';
                      statusColor = 'var(--error)';
                    } else if (qty < 10) {
                      statusText = 'Low Stock';
                      statusBg = 'rgba(245, 158, 11, 0.15)';
                      statusColor = '#fbbf24';
                    } else {
                      statusText = 'In Stock';
                      statusBg = 'rgba(34, 197, 94, 0.15)';
                      statusColor = 'var(--success)';
                    }
                  }

                  return (
                    <tr key={product.id} style={{ borderBottom: '1px solid var(--border)' }}>
                      <td style={{ padding: '1.25rem 0' }}>
                        <div style={{ fontWeight: '600' }}>{product.name}</div>
                        <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '0.25rem' }}>{product.id}</div>
                      </td>
                      
                      <td style={{ color: 'var(--text-muted)' }}>{product.category}</td>
                      
                      <td style={{ fontWeight: '600' }}>${product.price.toFixed(2)}</td>
                      
                      <td>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                          <span style={{ fontSize: '1.1rem', fontWeight: '700' }}>{qty}</span>
                          <span style={{ 
                            fontSize: '0.75rem', 
                            padding: '0.25rem 0.6rem', 
                            borderRadius: '20px', 
                            background: statusBg, 
                            color: statusColor, 
                            fontWeight: '600',
                            textTransform: 'uppercase'
                          }}>
                            {statusText}
                          </span>
                        </div>
                      </td>

                      <td style={{ textAlign: 'right' }}>
                        <div style={{ display: 'inline-flex', gap: '0.5rem', alignItems: 'center' }}>
                          <input 
                            type="number" 
                            min="0"
                            placeholder={qty.toString()}
                            value={replenishAmounts[product.id] || ''}
                            onChange={(e) => setReplenishAmounts(prev => ({ ...prev, [product.id]: e.target.value }))}
                            style={{ width: '80px', background: 'rgba(255,255,255,0.05)', border: '1px solid var(--border)', borderRadius: '8px', padding: '0.4rem 0.6rem', color: 'white', outline: 'none', textAlign: 'center' }}
                          />
                          <button 
                            onClick={() => handleUpdateStock(product, replenishAmounts[product.id] || qty)}
                            className="btn-primary" 
                            style={{ padding: '0.45rem 1rem', fontSize: '0.85rem', borderRadius: '8px' }}
                          >
                            {isInitialized ? 'Update' : 'Initialize'}
                          </button>
                        </div>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
};

const statCardStyle = {
  background: 'var(--bg-card)', 
  borderRadius: '16px', 
  border: '1px solid var(--border)', 
  padding: '1.5rem',
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-between'
};

export default InventoryManagement;
