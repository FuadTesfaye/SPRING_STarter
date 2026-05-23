export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  comparePrice?: number;
  category: string;
  images: string[];
  stock: number;
  rating: number;
  reviews: number;
  featured?: boolean;
  categoryAttributes?: CategoryAttributes;
}

export interface CategoryAttributes {
  // Clothing attributes
  size?: string;
  color?: string;
  material?: string;
  brand?: string;
  gender?: 'men' | 'women' | 'unisex' | 'kids';
  
  // Shoes specific
  shoeSize?: string;
  shoeType?: string;
  
  // Electronics specific
  model?: string;
  warranty?: string;
  
  // Books specific
  author?: string;
  isbn?: string;
  genre?: string;
  
  // General attributes
  weight?: string;
  dimensions?: string;
}

export interface CartItem {
  product: Product;
  quantity: number;
}

export interface Cart {
  items: CartItem[];
  total: number;
  itemCount: number;
}
