// Re-export all product/meal types for backwards compatibility
// Many service files import from 'types/product' while the canonical types live in 'types/meal'
export type { Product, CartItem, Cart, CategoryAttributes } from './meal';
