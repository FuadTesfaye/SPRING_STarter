import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ShoppingCart, Heart, ShieldCheck, ArrowLeft, Star, Sparkles, Check, ChevronRight } from 'lucide-react';
import { useApp } from '../context/AppContext';
import { api } from '../services/api';

const FALLBACK_PRODUCTS = [
  { id: 1, name: 'Quantum Laptop', price: 1299, description: 'Next-gen computing with sleek titanium finish.', image: '/products/laptop.png', category: 'Computers' },
  { id: 2, name: 'Neural Buds', price: 199, description: 'Experience sound like never before with ultimate spatial details.', image: '/products/buds.png', category: 'Audio' },
  { id: 3, name: 'Infinity Watch', price: 349, description: 'Elegance on your wrist, power in your hands with health tracking.', image: '/products/watch.png', category: 'Accessories' },
  { id: 4, name: 'Core Tablet', price: 799, description: 'The ultimate tool for creators and professionals.', image: '/products/tablet.png', category: 'Computers' },
  { id: 5, name: 'Aero Mouse', price: 89, description: 'Precision gaming with ultra-low latency optical sensor.', image: '/products/laptop.png', category: 'Accessories' },
  { id: 6, name: 'Lumina Keyboard', price: 159, description: 'RGB perfection with mechanical tactile feedback and wireless connection.', image: '/products/laptop.png', category: 'Accessories' }
];

const ProductDetail = () => {
  const { id } = useParams();
  const { addToCart, toggleWishlist, isInWishlist } = useApp();

  const [product, setProduct] = useState(null);
  const [related, setRelated] = useState([]);
  const [loading, setLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const [zoomStyle, setZoomStyle] = useState({ transformOrigin: '0% 0%', transform: 'scale(1)' });

  useEffect(() => {
    const fetchDetail = async () => {
      setLoading(true);
      try {
        const prodId = parseInt(id) || id;
        let data = null;
        try {
          data = await api.getProductById(prodId);
        } catch (e) {
          console.warn(`Product API failed for id ${prodId}, finding in fallback`);
        }

        const found = data || FALLBACK_PRODUCTS.find(p => p.id === Number(prodId) || p.id === prodId);
        if (found) {
          setProduct(found);
          // Set related items
          const relatedFiltered = FALLBACK_PRODUCTS.filter(p => p.id !== found.id && p.category === found.category).slice(0, 3);
          setRelated(relatedFiltered.length > 0 ? relatedFiltered : FALLBACK_PRODUCTS.filter(p => p.id !== found.id).slice(0, 3));
        } else {
          setProduct(null);
        }
      } catch (err) {
        console.error('Error fetching details:', err);
      } finally {
        setLoading(false);
      }
    };
    fetchDetail();
    setQuantity(1);
  }, [id]);

  const handleMouseMove = (e) => {
    const { left, top, width, height } = e.target.getBoundingClientRect();
    const x = ((e.pageX - left - window.scrollX) / width) * 100;
    const y = ((e.pageY - top - window.scrollY) / height) * 100;
    setZoomStyle({
      transformOrigin: `${x}% ${y}%`,
      transform: 'scale(1.8)'
    });
  };

  const handleMouseLeave = () => {
    setZoomStyle({ transformOrigin: '0% 0%', transform: 'scale(1)' });
  };

  if (loading) {
    return (
      <div className="container page-content" style={{ textAlign: 'center', paddingTop: '150px' }}>
        <div className="skeleton" style={{ height: '400px', maxWidth: '800px', margin: '0 auto' }} />
      </div>
    );
  }

  if (!product) {
    return (
      <div className="container page-content" style={{ textAlign: 'center', paddingTop: '150px' }}>
        <h2>Product Not Found</h2>
        <p style={{ color: 'var(--text-muted)', margin: '1rem 0 2rem' }}>We couldn't load this product.</p>
        <Link to="/shop" className="btn-primary">Back to Catalog</Link>
      </div>
    );
  }

  const wishlist = isInWishlist(product.id);

  return (
    <div className="container page-content" style={{ paddingTop: '120px' }}>
      {/* Breadcrumb */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', color: 'var(--text-muted)', fontSize: '0.85rem', marginBottom: '2rem' }}>
        <Link to="/" style={{ hover: 'color: var(--text-main)' }}>Home</Link>
        <ChevronRight size={12} />
        <Link to="/shop">Shop</Link>
        <ChevronRight size={12} />
        <span style={{ color: 'var(--text-main)', fontWeight: '600' }}>{product.name}</span>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '4rem', marginBottom: '5rem' }}>
        {/* Product Image Panel with Zoom */}
        <div style={{ position: 'relative' }}>
          <div
            onMouseMove={handleMouseMove}
            onMouseLeave={handleMouseLeave}
            style={{
              height: '460px', background: 'var(--bg-card)', borderRadius: 'var(--radius-lg)',
              border: '1px solid var(--border)', overflow: 'hidden', cursor: 'zoom-in', position: 'relative'
            }}
          >
            <img
              src={product.image || 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=600&q=80'}
              alt={product.name}
              style={{
                width: '100%', height: '100%', objectFit: 'cover', transition: 'transform 0.1s ease',
                ...zoomStyle
              }}
            />
          </div>
        </div>

        {/* Product Details Panel */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }} className="animate-fade">
          <div>
            <div style={{ display: 'inline-flex', alignItems: 'center', gap: '0.4rem', background: 'var(--primary-glow)', color: 'var(--primary)', padding: '0.25rem 0.75rem', borderRadius: 'var(--radius-full)', fontSize: '0.78rem', fontWeight: '700', textTransform: 'uppercase', marginBottom: '0.75rem' }}>
              <Sparkles size={12} /> Premium Device
            </div>
            <h1 style={{ fontSize: '2.5rem', fontWeight: '800', lineHeight: '1.2', marginBottom: '0.5rem' }}>{product.name}</h1>
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
              <span style={{ color: 'var(--primary)', fontSize: '2rem', fontWeight: '800' }}>${product.price}</span>
              <div style={{ display: 'flex', alignItems: 'center', gap: '0.2rem', color: 'var(--warning)' }}>
                {[1, 2, 3, 4, 5].map(n => <Star key={n} size={15} fill="var(--warning)" />)}
                <span style={{ color: 'var(--text-muted)', fontSize: '0.8rem', marginLeft: '0.3rem' }}>(24 reviews)</span>
              </div>
            </div>
          </div>

          <hr style={{ border: 'none', borderTop: '1px solid var(--border)' }} />

          <p style={{ color: 'var(--text-muted)', fontSize: '1rem', lineHeight: '1.7' }}>
            {product.description || 'Elevate your everyday activities with our curated product designs. Handcrafted from top materials for the ultimate modern user experience.'}
          </p>

          {/* Features check list */}
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0.75rem' }}>
            {['Titanium alloy grade body', 'Ultra high-definition fidelity', 'Smart power save architecture', '3-year international warranty'].map(feat => (
              <div key={feat} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.85rem', color: 'var(--text-muted)' }}>
                <div style={{ background: 'var(--success-bg)', color: 'var(--success)', padding: '2px', borderRadius: '50%', display: 'flex' }}>
                  <Check size={12} />
                </div>
                <span>{feat}</span>
              </div>
            ))}
          </div>

          {/* Action Row */}
          <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', marginTop: '1.5rem' }}>
            {/* Quantity selector */}
            <div style={{ display: 'flex', alignItems: 'center', background: 'var(--bg-elevated)', borderRadius: 'var(--radius-md)', border: '1px solid var(--border)', padding: '0.3rem' }}>
              <button
                onClick={() => setQuantity(q => Math.max(1, q - 1))}
                style={{ width: '36px', height: '36px', background: 'none', color: 'var(--text-main)', fontSize: '1.2rem', fontWeight: '600' }}
              >-</button>
              <span style={{ minWidth: '30px', textAlign: 'center', fontWeight: '700' }}>{quantity}</span>
              <button
                onClick={() => setQuantity(q => q + 1)}
                style={{ width: '36px', height: '36px', background: 'none', color: 'var(--text-main)', fontSize: '1.2rem', fontWeight: '600' }}
              >+</button>
            </div>

            <button
              onClick={() => addToCart(product, quantity)}
              className="btn-primary"
              style={{ flex: 1, padding: '1rem' }}
            >
              <ShoppingCart size={18} /> Add {quantity} to Cart
            </button>

            <button
              onClick={() => toggleWishlist(product)}
              style={{
                width: '54px', height: '54px', border: '1px solid var(--border)', borderRadius: 'var(--radius-md)',
                background: 'var(--bg-card)', display: 'flex', alignItems: 'center', justifyContent: 'center',
                cursor: 'pointer', color: wishlist ? 'var(--error)' : 'var(--text-main)', transition: 'var(--transition)'
              }}
            >
              <Heart size={20} fill={wishlist ? 'var(--error)' : 'none'} />
            </button>
          </div>

          {/* Guarantee stamp */}
          <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center', background: 'var(--bg-elevated)', padding: '0.8rem 1.25rem', borderRadius: 'var(--radius-md)', border: '1px solid var(--border)', fontSize: '0.85rem', color: 'var(--text-muted)', marginTop: '0.5rem' }}>
            <ShieldCheck size={18} color="var(--success)" />
            <span>Secure 256-bit payment transaction guarantee. Fully backed by microservices.</span>
          </div>
        </div>
      </div>

      {/* Related Products */}
      <div>
        <h3 style={{ fontSize: '1.5rem', fontWeight: '800', marginBottom: '2rem' }}>You May Also Like</h3>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '2rem' }}>
          {related.map(p => {
            const wishes = isInWishlist(p.id);
            return (
              <div key={p.id} className="card card-product animate-fade" style={{ display: 'flex', flexDirection: 'column' }}>
                <div style={{ height: '180px', background: 'var(--bg-elevated)', position: 'relative', overflow: 'hidden' }}>
                  <img
                    src={p.image}
                    alt={p.name}
                    className="product-img"
                    style={{ width: '100%', height: '100%', objectFit: 'cover', transition: 'transform 0.5s ease' }}
                  />
                  <button
                    onClick={() => toggleWishlist(p)}
                    style={{
                      position: 'absolute', top: '10px', right: '10px', background: 'var(--glass)',
                      border: '1px solid var(--border)', width: '30px', height: '30px', borderRadius: '50%',
                      display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', zIndex: 5,
                      color: wishes ? 'var(--error)' : 'var(--text-main)'
                    }}
                  >
                    <Heart size={14} fill={wishes ? 'var(--error)' : 'none'} />
                  </button>
                </div>
                <div style={{ padding: '1.25rem' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '0.25rem' }}>
                    <Link to={`/product/${p.id}`} style={{ textDecoration: 'none' }}>
                      <h4 style={{ fontSize: '0.95rem', fontWeight: '700', color: 'var(--text-main)' }}>{p.name}</h4>
                    </Link>
                    <span style={{ color: 'var(--primary)', fontWeight: '800', fontSize: '0.95rem' }}>${p.price}</span>
                  </div>
                  <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem', marginBottom: '1rem', display: '-webkit-box', WebkitLineClamp: '2', WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>
                    {p.description}
                  </p>
                  <button
                    onClick={() => addToCart(p, 1)}
                    className="btn-primary"
                    style={{ width: '100%', padding: '0.5rem', fontSize: '0.8rem' }}
                  >
                    Add to Cart
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
