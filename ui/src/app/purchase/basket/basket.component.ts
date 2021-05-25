import { Component, OnChanges, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasketService } from 'src/app/shared/services/basket-service';
import { UserBasket } from 'src/app/_model/UserBasket';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent implements OnInit {
  checkoutRedirectPath: string = '/checkout';

  basket: UserBasket;

  constructor(router: Router, private basketService: BasketService) {
    this.basket = basketService.getBasket();
  }

  ngOnInit(): void {
    this.initBasket();
  }

  initBasket() {
    return this.basketService.getBasketObservable().subscribe((b) => (this.basket = b));
  }

  getBasketProducts() {
    return this.basket.getBasketProducts();
  }

  getBasketSize() {
    return this.basket.getBasketProducts().length;
  }

  getBasketValue() {
    return this.basket.getTotalPrice();
  }
}
