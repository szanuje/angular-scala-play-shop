import { BasketProduct } from "./BasketProduct";
import { Product } from "./Product";

export class UserBasket {
    basketProducts: BasketProduct[] = [];
    totalPrice: number = 0.0;

    constructor() {
        //
    }

    getBasketProducts() {
        return this.basketProducts;
    }

    getTotalPrice() {
        return this.totalPrice;
    }

    addProductToBasket(product: Product) {
        let idx = this.basketProducts.findIndex(bProd => bProd.product === product);
        if(idx == -1) {
            this.basketProducts.push({product: product, quantity: 1, totalPrice: product.price});
        } else {
            this.basketProducts[idx].quantity++;
            this.basketProducts[idx].totalPrice += product.price;
        }
        this.totalPrice += product.price;
    }
}