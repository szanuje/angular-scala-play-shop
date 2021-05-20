import { BasketProduct } from "src/app/_model/basket";
import { Product } from "src/app/_model/product";

export class BasketService {
    basket: BasketProduct = {};
    basketSize: number = 0;

    constructor() {
        //
    }

    addProductToBasket(product: Product): void {
        if (this.basket[product.name]) {
          let newPrice = this.basket[product.name].totalPrice + product.price;
          this.basket[product.name].quantity++;
          this.basket[product.name].totalPrice = +newPrice.toFixed(2);
        } else {
          this.basket[product.name] = {
            product: product,
            quantity: 1,
            totalPrice: +product.price.toFixed(2)
          }
        }
        this.basketSize++;
      }

    getBasketProducts(): BasketProduct {
        return this.basket;
    }


    getBasketSize(): number {
        return this.basketSize;
    }
}