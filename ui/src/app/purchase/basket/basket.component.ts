import { Component, OnInit } from '@angular/core';
import { BasketService } from 'src/app/shared/services/basket-service';
import { BasketProduct } from 'src/app/_model/BasketProduct';
import { UserBasket } from 'src/app/_model/UserBasket';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent implements OnInit {
  checkoutRedirectPath: string = '/checkout';
  basket: UserBasket;

  constructor(
    private basketService: BasketService,
  ) {
    this.basket = this.basketService.getBasket();
  }

  ngOnInit(): void {
    this.basketService.getBasketSubject().subscribe(b => this.basket = b);
  }

  getBasket() {
    return this.basket;
  }

  getBasketProducts(): BasketProduct[] {
    return this.basket.getBasketProducts();
  }

  getBasketSize(): number {
    return this.basket.getBasketProducts().length;
  }

  getBasketValue(): number {
    return this.basket.getTotalPrice();
  }

  saveUserBasket(): void {
    this.basketService.saveUserBasket();
  }
}
