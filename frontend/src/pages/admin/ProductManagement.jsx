import React, { useState, useEffect } from 'react';
import Sidebar from '../../components/admin/Sidebar';
import { Plus, Trash2, Edit2, Search } from 'lucide-react';
import { api } from '../../services/api';

const ProductManagement = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const data = await api.getProducts();
      setProducts(data);
    } catch (err) {
      console.error('Failed to fetch products', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await api.deleteProduct(id);
        fetchProducts();
      } catch (err) {
        console.error('Delete failed', err);
      }
    }
  };

  return (
    <div style={{ display: 'flex', minHeight: '100vh', background: 'var(--bg-dark)' }}>
      <Sidebar />
      
      <main style={{ flex: 1, marginLeft: '280px', padding: '3rem' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '3rem' }}>
          <div>
            <h1 style={{ fontSize: '2rem', fontWeight: '700', marginBottom: '0.5rem' }}>Product Management</h1>
            <p style={{ color: 'var(--text-muted)' }}>Manage your catalog and stock levels.</p>
          </div>
          <button className="btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
            <Plus size={20} /> Add Product
          </button>
        </div>

        <div style={{ background: 'var(--bg-card)', borderRadius: '16px', border: '1px solid var(--border)', padding: '2rem' }}>
          <div style={{ marginBottom: '2rem', display: 'flex', gap: '1rem' }}>
             <div style={{ position: 'relative', flex: 1 }}>
                <Search size={18} style={{ position: 'absolute', left: '1rem', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                <input 
                  type="text" 
                  placeholder="Filter products..." 
                  style={{ width: '100%', background: 'rgba(255,255,255,0.05)', border: '1px solid var(--border)', borderRadius: '10px', padding: '0.75rem 1rem 0.75rem 3rem', color: 'white', outline: 'none' }}
                />
             </div>
          </div>

          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ textAlign: 'left', borderBottom: '1px solid var(--border)', color: 'var(--text-muted)' }}>
                <th style={{ padding: '1rem 0' }}>Product Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
                <th style={{ textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.length === 0 ? (
                <tr>
                  <td colSpan="5" style={{ textAlign: 'center', padding: '4rem 0', color: 'var(--text-muted)' }}>
                    {isLoading ? 'Loading catalog...' : 'No products found. Start by adding one.'}
                  </td>
                </tr>
              ) : (
                products.map((product) => (
                  <tr key={product.id} style={{ borderBottom: '1px solid var(--border)' }}>
                    <td style={{ padding: '1rem 0', fontWeight: '500' }}>{product.name}</td>
                    <td>{product.category}</td>
                    <td style={{ fontWeight: '600' }}>${product.price}</td>
                    <td>
                       <span style={{ 
                         color: product.stockQuantity < 10 ? 'var(--error)' : 'var(--text-main)',
                         fontWeight: product.stockQuantity < 10 ? 'bold' : 'normal'
                       }}>
                         {product.stockQuantity} units
                       </span>
                    </td>
                    <td style={{ textAlign: 'right' }}>
                      <button style={{ background: 'none', color: 'var(--text-muted)', marginRight: '1rem' }}><Edit2 size={18} /></button>
                      <button 
                        onClick={() => handleDelete(product.id)}
                        style={{ background: 'none', color: 'var(--error)' }}
                      >
                        <Trash2 size={18} />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
};

export default ProductManagement;
