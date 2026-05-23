import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, ShoppingCart, Trash2, ArrowLeft } from 'lucide-react';
import { useApp } from '../context/AppContext';

const Wishlist = () => {
  const { wishlistItems, toggleWishlist, addToCart } = useApp();

  return (
    <div className="container page-content" style={{ paddingTop: '120px' }}>
      <h2 style={{ fontSize: '2.5rem', fontWeight: '800', marginBottom: '2.5rem', display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
        <Heart size={32} fill="var(--error)" color="var(--error)" /> My Wishlist
      </h2>

      {wishlistItems.length === 0 ? (
        <div className="card" style={{ padding: '4rem 2rem', textAlign: 'center', maxWidth: '600px', margin: '0 auto' }}>
          <Heart size={56} style={{ color: 'var(--text-subtle)', marginBottom: '1.5rem', opacity: 0.3 }} />
          <h3 style={{ fontSize: '1.3rem', marginBottom: '0.5rem' }}>Your Wishlist is Empty</h3>
          <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '2rem' }}>
            Tap the heart icon on any premium product to save it here for later.
          </p>
          <Link to="/shop" className="btn-primary" style={{ display: 'inline-flex', gap: '0.5rem', alignItems: 'center' }}>
            <ArrowLeft size={16} /> Continue Shopping
          </Link>
        </div>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: '2rem' }}>
          {wishlistItems.map((product) => (
            <div key={product.id} className="card card-product animate-fade" style={{ display: 'flex', flexDirection: 'column' }}>
              {/* Product Image */}
              <div style={{ height: '220px', background: 'var(--bg-elevated)', position: 'relative', overflow: 'hidden' }}>
                <img
                  src={product.image || 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=400&q=80'}
                  alt={product.name}
                  className="product-img"
                  style={{ width: '100%', height: '100%', objectFit: 'cover', transition: 'transform 0.5s ease' }}
                />
                <button
                  onClick={() => toggleWishlist(product)}
                  style={{
                    position: 'absolute', top: '12px', right: '12px', background: 'var(--glass)',
                    border: '1px solid var(--border)', width: '36px', height: '36px', borderRadius: '50%',
                    display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', zIndex: 5,
                    color: 'var(--error)'
                  }}
                >
                  <Heart size={16} fill="var(--error)" />
                </button>
              </div>

              {/* Product Info */}
              <div style={{ padding: '1.5rem', display: 'flex', flexDirection: 'column', flex: 1, justifyContent: 'space-between' }}>
                <div>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '0.4rem', gap: '0.5rem' }}>
                    <Link to={`/product/${product.id}`} style={{ textDecoration: 'none' }}>
                      <h3 style={{ fontSize: '1rem', fontWeight: '700', color: 'var(--text-main)' }}>{product.name}</h3>
                    </Link>
                    <span style={{ color: 'var(--primary)', fontWeight: '800', fontSize: '1.05rem', whiteSpace: 'nowrap' }}>
                      ${product.price}
                    </span>
                  </div>
                  <p style={{ color: 'var(--text-muted)', fontSize: '0.82rem', marginBottom: '1.5rem', display: '-webkit-box', WebkitLineClamp: '2', WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>
                    {product.description}
                  </p>
                </div>

                <div style={{ display: 'flex', gap: '0.75rem' }}>
                  <button
                    onClick={() => {
                      addToCart(product, 1);
                      toggleWishlist(product); // Remove from wishlist when added to cart
                    }}
                    className="btn-primary"
                    style={{ flex: 1, padding: '0.65rem', fontSize: '0.85rem' }}
                  >
                    <ShoppingCart size={14} /> Move to Cart
                  </button>
                  <button
                    onClick={() => toggleWishlist(product)}
                    className="btn-secondary"
                    style={{ padding: '0.65rem', color: 'var(--error)', borderColor: 'var(--border)' }}
                  >
                    <Trash2 size={14} />
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Wishlist;
