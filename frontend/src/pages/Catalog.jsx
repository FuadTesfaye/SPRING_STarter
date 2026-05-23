import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ProductCard from '../components/products/ProductCard';
import { Search, SlidersHorizontal, Grid, List, Sparkles, AlertCircle } from 'lucide-react';
import { api } from '../services/api';

const DUMMY_PRODUCTS = [
  { id: 1, name: 'Quantum Laptop', price: 1299, description: 'Next-gen computing with sleek titanium finish.', image: '/products/laptop.png', category: 'Computers', stockQuantity: 15 },
  { id: 2, name: 'Neural Buds', price: 199, description: 'Experience sound like never before.', image: '/products/buds.png', category: 'Audio', stockQuantity: 5 },
  { id: 3, name: 'Infinity Watch', price: 349, description: 'Elegance on your wrist, power in your hands.', image: '/products/watch.png', category: 'Accessories', stockQuantity: 24 },
  { id: 4, name: 'Core Tablet', price: 799, description: 'The ultimate tool for creators and professionals.', image: '/products/tablet.png', category: 'Computers', stockQuantity: 0 },
  { id: 5, name: 'Aero Mouse', price: 89, description: 'Precision gaming with ultra-low latency.', image: '/products/laptop.png', category: 'Accessories', stockQuantity: 42 },
  { id: 6, name: 'Lumina Keyboard', price: 159, description: 'RGB perfection with mechanical tactile feedback.', image: '/products/laptop.png', category: 'Accessories', stockQuantity: 7 }
];

const Catalog = () => {
  const location = useLocation();

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [viewMode, setViewMode] = useState('grid'); // 'grid' | 'list'

  // Filters State
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('All');
  const [maxPrice, setMaxPrice] = useState(1500);
  const [sortBy, setSortBy] = useState('popular'); // 'popular' | 'lowToHigh' | 'highToLow' | 'name'

  // Categories list
  const categories = ['All', 'Computers', 'Audio', 'Accessories'];

  useEffect(() => {
    const fetchCatalog = async () => {
      setLoading(true);
      try {
        const data = await api.getProducts();
        if (Array.isArray(data) && data.length > 0) {
          setProducts(data);
        } else {
          setProducts(DUMMY_PRODUCTS);
        }
      } catch (err) {
        console.warn('Backend products API failed or refused connection. Using fallbacks.', err);
        setProducts(DUMMY_PRODUCTS);
      } finally {
        setLoading(false);
      }
    };
    fetchCatalog();
  }, []);

  // Sync search query from URL ?q=
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const q = params.get('q');
    if (q !== null) {
      setSearch(q);
    }
  }, [location.search]);

  // Filter and sort computation
  const filteredProducts = products
    .filter(p => {
      const matchSearch = p.name.toLowerCase().includes(search.toLowerCase()) ||
                          p.description?.toLowerCase().includes(search.toLowerCase());
      const matchCat = category === 'All' || p.category === category;
      const matchPrice = p.price <= maxPrice;
      return matchSearch && matchCat && matchPrice;
    })
    .sort((a, b) => {
      if (sortBy === 'lowToHigh') return a.price - b.price;
      if (sortBy === 'highToLow') return b.price - a.price;
      if (sortBy === 'name') return a.name.localeCompare(b.name);
      return b.id - a.id; // 'popular' / default order
    });

  return (
    <div className="container page-content" style={{ paddingTop: '120px' }}>
      <header style={{ marginBottom: '3.5rem', textAlign: 'center' }}>
        <div style={{ display: 'inline-flex', alignItems: 'center', gap: '0.4rem', background: 'var(--primary-glow)', color: 'var(--primary)', padding: '0.3rem 0.8rem', borderRadius: 'var(--radius-full)', fontSize: '0.75rem', fontWeight: '700', textTransform: 'uppercase', marginBottom: '0.75rem' }}>
          <Sparkles size={12} /> Curated Tech Goods
        </div>
        <h1 style={{ fontSize: '3.2rem', fontWeight: '900', marginBottom: '0.75rem', letterSpacing: '-1px' }}>
          Discover <span className="gradient-text">Excellence</span>
        </h1>
        <p style={{ color: 'var(--text-muted)', fontSize: '1.1rem', maxWidth: '580px', margin: '0 auto' }}>
          Elevate your productivity and gaming environment with our collection of high-performance tech.
        </p>
      </header>

      {/* Main Catalog Grid layout */}
      <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: '3rem' }}>
        {/* Sidebar Filters */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', borderBottom: '1px solid var(--border)', paddingBottom: '1rem' }}>
            <SlidersHorizontal size={18} color="var(--primary)" />
            <h3 style={{ fontSize: '1.1rem', fontWeight: '700' }}>Filter & Refine</h3>
          </div>

          {/* Search filter in sidebar */}
          <div>
            <label style={{ display: 'block', fontSize: '0.85rem', fontWeight: '600', color: 'var(--text-muted)', marginBottom: '0.5rem' }}>Search Product</label>
            <div style={{ position: 'relative', display: 'flex', alignItems: 'center' }}>
              <Search size={16} style={{ position: 'absolute', left: '1rem', color: 'var(--text-subtle)' }} />
              <input
                type="text"
                value={search}
                onChange={e => setSearch(e.target.value)}
                placeholder="Type keywords..."
                className="input"
                style={{ paddingLeft: '2.5rem', height: '42px', fontSize: '0.85rem' }}
              />
            </div>
          </div>

          {/* Category Tabs list */}
          <div>
            <label style={{ display: 'block', fontSize: '0.85rem', fontWeight: '600', color: 'var(--text-muted)', marginBottom: '0.75rem' }}>Category</label>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.4rem' }}>
              {categories.map(cat => (
                <button
                  key={cat}
                  onClick={() => setCategory(cat)}
                  style={{
                    textAlign: 'left', padding: '0.6rem 1rem', borderRadius: '8px', fontSize: '0.88rem', fontWeight: '500',
                    background: category === cat ? 'var(--primary-glow)' : 'none',
                    color: category === cat ? 'var(--primary)' : 'var(--text-muted)',
                    transition: 'var(--transition)', width: '100%', border: 'none'
                  }}
                  onMouseEnter={e => { if (category !== cat) e.currentTarget.style.color = 'var(--text-main)'; }}
                  onMouseLeave={e => { if (category !== cat) e.currentTarget.style.color = 'var(--text-muted)'; }}
                >
                  {cat}
                </button>
              ))}
            </div>
          </div>

          {/* Price Range Slider */}
          <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', fontWeight: '600', color: 'var(--text-muted)', marginBottom: '0.5rem' }}>
              <span>Max Price</span>
              <span style={{ color: 'var(--primary)', fontWeight: '700' }}>${maxPrice}</span>
            </div>
            <input
              type="range"
              min="50"
              max="2000"
              step="50"
              value={maxPrice}
              onChange={e => setMaxPrice(Number(e.target.value))}
              style={{ width: '100%', accentColor: 'var(--primary)', cursor: 'pointer' }}
            />
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.75rem', color: 'var(--text-subtle)', marginTop: '0.25rem' }}>
              <span>$50</span>
              <span>$2000</span>
            </div>
          </div>
        </div>

        {/* Catalog List / Content Area */}
        <div>
          {/* Top Sort / Display Bar */}
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', background: 'var(--bg-card)', padding: '1rem 1.5rem', borderRadius: 'var(--radius-md)', border: '1px solid var(--border)', marginBottom: '2rem' }}>
            <span style={{ fontSize: '0.88rem', color: 'var(--text-muted)' }}>
              Showing <strong style={{ color: 'var(--text-main)' }}>{filteredProducts.length}</strong> premium goods
            </span>

            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
              {/* SortDropdown */}
              <select
                value={sortBy}
                onChange={e => setSortBy(e.target.value)}
                style={{
                  background: 'var(--bg-elevated)', border: '1px solid var(--border)', color: 'var(--text-main)',
                  padding: '0.4rem 0.8rem', borderRadius: '8px', fontSize: '0.85rem', outline: 'none'
                }}
              >
                <option value="popular">Latest Arrivals</option>
                <option value="lowToHigh">Price: Low to High</option>
                <option value="highToLow">Price: High to Low</option>
                <option value="name">Name: A to Z</option>
              </select>

              {/* View toggle */}
              <div style={{ display: 'flex', gap: '0.25rem', background: 'var(--bg-elevated)', padding: '2px', borderRadius: '8px', border: '1px solid var(--border)' }}>
                <button onClick={() => setViewMode('grid')} style={{ padding: '0.3rem', borderRadius: '6px', background: viewMode === 'grid' ? 'var(--bg-card)' : 'none', color: viewMode === 'grid' ? 'var(--primary)' : 'var(--text-muted)' }}><Grid size={16} /></button>
                <button onClick={() => setViewMode('list')} style={{ padding: '0.3rem', borderRadius: '6px', background: viewMode === 'list' ? 'var(--bg-card)' : 'none', color: viewMode === 'list' ? 'var(--primary)' : 'var(--text-muted)' }}><List size={16} /></button>
              </div>
            </div>
          </div>

          {/* Catalog grid renderer */}
          {loading ? (
            <div style={{ display: 'grid', gridTemplateColumns: viewMode === 'grid' ? 'repeat(auto-fill, minmax(250px, 1fr))' : '1fr', gap: '2rem' }}>
              {[1, 2, 3, 4, 5, 6].map(n => (
                <div key={n} className="skeleton" style={{ height: viewMode === 'grid' ? '360px' : '140px' }} />
              ))}
            </div>
          ) : filteredProducts.length === 0 ? (
            <div className="card animate-fade" style={{ padding: '4rem 2rem', textAlign: 'center' }}>
              <AlertCircle size={44} style={{ color: 'var(--text-subtle)', marginBottom: '1rem', opacity: 0.4 }} />
              <h3>No Matches Found</h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.88rem', margin: '0.5rem 0 1.5rem' }}>
                We couldn't find any premium goods matching your current search filters.
              </p>
              <button
                onClick={() => { setSearch(''); setCategory('All'); setMaxPrice(1500); }}
                className="btn-secondary"
                style={{ fontSize: '0.82rem' }}
              >Reset All Filters</button>
            </div>
          ) : viewMode === 'grid' ? (
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(265px, 1fr))', gap: '2rem' }} className="stagger">
              {filteredProducts.map(product => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          ) : (
            // List View layout
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
              {filteredProducts.map(product => (
                <div key={product.id} className="card animate-fade" style={{ display: 'flex', gap: '1.5rem', padding: '1.25rem' }}>
                  <img
                    src={product.image || 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=150&q=80'}
                    alt={product.name}
                    style={{ width: '130px', height: '130px', objectFit: 'cover', borderRadius: '10px', background: 'var(--bg-elevated)' }}
                  />
                  <div style={{ flex: 1, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                    <div>
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <h3 style={{ fontSize: '1.1rem', fontWeight: '700' }}>{product.name}</h3>
                        <span style={{ color: 'var(--primary)', fontWeight: '800', fontSize: '1.2rem' }}>${product.price}</span>
                      </div>
                      <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem', marginTop: '0.4rem', display: '-webkit-box', WebkitLineClamp: '2', WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>
                        {product.description}
                      </p>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '1rem' }}>
                      <span className="pill pill-primary" style={{ fontSize: '0.72rem' }}>{product.category}</span>
                      <button
                        onClick={() => api.addProduct(product)} // Add product utility
                        className="btn-primary"
                        style={{ padding: '0.5rem 1.25rem', fontSize: '0.8rem' }}
                      >View Product Details</button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Catalog;
