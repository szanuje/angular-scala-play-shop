import { Product } from "./product";

export interface BasketProduct {
    [name: string]: {
        product: Product;
        quantity: number;
        totalPrice: number;
    }
}