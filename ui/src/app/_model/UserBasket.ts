import { BasketProduct } from './BasketProduct';
import { Product } from './Product';

export class UserBasket {
  basketProducts: BasketProduct[] = [];
  totalPrice: number = 0.0;

  constructor() {
    //
  }

  getBasketProducts(): BasketProduct[] {
    return this.basketProducts;
  }

  getTotalPrice(): number {
    return this.totalPrice;
  }

  addProductToBasket(product: Product): void {
    let idx = this.basketProducts.findIndex(
      (bProd) => bProd.product === product
    );
    if (idx == -1) {
      this.basketProducts.push({
        product: product,
        quantity: 1,
        totalPrice: (product.price * 100) / 100,
      });
    } else {
      this.basketProducts[idx].quantity++;
      this.basketProducts[idx].totalPrice = ( (this.basketProducts[idx].totalPrice * 100) + (product.price * 100) ) / 100;
      console.log(this.basketProducts[idx].totalPrice);
    }
    this.totalPrice = ( this.totalPrice * 100 + product.price * 100 ) / 100;
  }
}
