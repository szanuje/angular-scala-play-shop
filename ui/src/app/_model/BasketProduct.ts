import { Product } from './Product';

export interface BasketProduct {
  product: Product;
  quantity: number;
  totalPrice: number;
}
