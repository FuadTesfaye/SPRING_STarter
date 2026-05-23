import React, { useState } from 'react';
import { ShoppingCart, Heart, Eye, Plus, Minus } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useApp } from '../../context/AppContext';

const ProductCard = ({ product }) => {
  const { addToCart, toggleWishlist, isInWishlist } = useApp();
  const [qty, setQty] = useState(1);
  const wishlist = isInWishlist(product.id);

  // Check inventory levels
  const isOutOfStock = product.stockQuantity !== undefined && product.stockQuantity <= 0;
  const isLowStock = product.stockQuantity !== undefined && product.stockQuantity > 0 && product.stockQuantity <= 8;

  return (
    <div className="card card-product animate-fade" style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* Product Image Area */}
      <div style={{ height: '220px', background: 'var(--bg-elevated)', position: 'relative', overflow: 'hidden' }}>
        <img
          src={product.image || 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=400&q=80'}
          alt={product.name}
          className="product-img"
          style={{ width: '100%', height: '100%', objectFit: 'cover', transition: 'transform 0.5s cubic-bezier(0.4, 0, 0.2, 1)' }}
        />

        {/* Stock Status Badge */}
        {isOutOfStock ? (
          <span className="pill pill-error" style={{ position: 'absolute', top: '12px', left: '12px', zIndex: 5, fontSize: '0.65rem' }}>Out of Stock</span>
        ) : isLowStock ? (
          <span className="pill pill-warning" style={{ position: 'absolute', top: '12px', left: '12px', zIndex: 5, fontSize: '0.65rem' }}>Only {product.stockQuantity} Left</span>
        ) : (
          <span className="pill pill-success" style={{ position: 'absolute', top: '12px', left: '12px', zIndex: 5, fontSize: '0.65rem' }}>In Stock</span>
        )}

        {/* Wishlist Heart Toggle */}
        <button
          onClick={() => toggleWishlist(product)}
          style={{
            position: 'absolute', top: '12px', right: '12px', background: 'var(--glass)',
            border: '1px solid var(--border)', width: '34px', height: '34px', borderRadius: '50%',
            display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', zIndex: 5,
            color: wishlist ? 'var(--error)' : 'var(--text-main)', transition: 'var(--transition)'
          }}
        >
          <Heart size={15} fill={wishlist ? 'var(--error)' : 'none'} />
        </button>

        {/* Quick View link overlay */}
        <div style={{
          position: 'absolute', inset: 0, background: 'rgba(7,9,15,0.4)',
          opacity: 0, display: 'flex', alignItems: 'center', justifyContent: 'center',
          transition: 'var(--transition)', zIndex: 2
        }}
          className="card-overlay"
          onMouseEnter={e => e.currentTarget.style.opacity = 1}
          onMouseLeave={e => e.currentTarget.style.opacity = 0}
        >
          <Link to={`/product/${product.id}`} className="btn-secondary" style={{ padding: '0.5rem 1rem', display: 'flex', alignItems: 'center', gap: '0.4rem', fontSize: '0.8rem', borderRadius: '8px' }}>
            <Eye size={14} /> Quick View
          </Link>
        </div>
      </div>

      {/* Info details */}
      <div style={{ padding: '1.25rem', display: 'flex', flexDirection: 'column', flex: 1, justifyContent: 'space-between' }}>
        <div>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '0.4rem', gap: '0.5rem' }}>
            <Link to={`/product/${product.id}`} style={{ textDecoration: 'none' }}>
              <h3 style={{ fontSize: '0.95rem', fontWeight: '700', color: 'var(--text-main)', transition: 'var(--transition)' }}
                onMouseEnter={e => e.currentTarget.style.color = 'var(--primary)'}
                onMouseLeave={e => e.currentTarget.style.color = 'var(--text-main)'}
              >
                {product.name}
              </h3>
            </Link>
            <span style={{ color: 'var(--primary)', fontWeight: '800', fontSize: '1.05rem', whiteSpace: 'nowrap' }}>
              ${product.price}
            </span>
          </div>

          <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem', marginBottom: '1.25rem', display: '-webkit-box', WebkitLineClamp: '2', WebkitBoxOrient: 'vertical', overflow: 'hidden', lineHeight: '1.4' }}>
            {product.description || 'Premium design system crafted with onion microservice architecture.'}
          </p>
        </div>

        {/* Quantity and Cart row */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
          {!isOutOfStock && (
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', gap: '0.5rem' }}>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Quantity:</span>
              <div style={{ display: 'flex', alignItems: 'center', background: 'var(--bg-elevated)', borderRadius: '8px', padding: '0.2rem', border: '1px solid var(--border)' }}>
                <button
                  disabled={qty <= 1}
                  onClick={() => setQty(q => Math.max(1, q - 1))}
                  style={{ width: '22px', height: '22px', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'none', border: 'none', color: 'var(--text-muted)', cursor: 'pointer', borderRadius: '4px' }}
                >
                  <Minus size={11} />
                </button>
                <span style={{ fontSize: '0.8rem', fontWeight: '700', minWidth: '18px', textAlign: 'center' }}>{qty}</span>
                <button
                  onClick={() => setQty(q => q + 1)}
                  style={{ width: '22px', height: '22px', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'none', border: 'none', color: 'var(--text-muted)', cursor: 'pointer', borderRadius: '4px' }}
                >
                  <Plus size={11} />
                </button>
              </div>
            </div>
          )}

          <button
            onClick={() => {
              addToCart(product, qty);
              setQty(1);
            }}
            disabled={isOutOfStock}
            className="btn-primary"
            style={{ width: '100%', padding: '0.6rem', fontSize: '0.82rem', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '0.4rem' }}
          >
            <ShoppingCart size={13} /> {isOutOfStock ? 'Sold Out' : 'Add to Cart'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
