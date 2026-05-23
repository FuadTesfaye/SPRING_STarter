export interface Restaurant {
  id: string;
  name: string;
  description: string;
  cuisine: string;
  rating: number;
  reviewCount: number;
  deliveryTime: string;
  deliveryFee: number;
  minOrder: number;
  imageUrl: string;
  featured: boolean;
  isOpen: boolean;
  hours?: string;
}

export interface MenuItem {
  id: string;
  restaurantId: string;
  name: string;
  description: string;
  price: number;
  category: string;
  imageUrl: string;
  popular: boolean;
  available: boolean;
}

export interface FoodOrder {
  id: string;
  userId: string;
  restaurantId: string;
  restaurantName: string;
  items: FoodOrderItem[];
  deliveryAddress: DeliveryAddress;
  total: number;
  status: OrderStatus;
  createdAt: string;
  estimatedDelivery: string;
}

export interface FoodOrderItem {
  menuItemId: string;
  name: string;
  price: number;
  quantity: number;
}

export interface DeliveryAddress {
  street: string;
  city: string;
  zipCode: string;
  phone: string;
  instructions?: string;
}

export type OrderStatus = 
  | 'pending'
  | 'confirmed'
  | 'preparing'
  | 'ready'
  | 'on_the_way'
  | 'delivered'
  | 'cancelled';

export interface CartFoodItem {
  menuItem: MenuItem;
  quantity: number;
  restaurantId: string;
  restaurantName: string;
}

export interface FoodCart {
  items: CartFoodItem[];
  restaurantId: string | null;
  restaurantName: string | null;
  subtotal: number;
  deliveryFee: number;
  total: number;
}
