import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasketService } from 'src/app/shared/services/basket-service';
import { BasketProduct } from 'src/app/_model/basket';
import { Product } from '../../_model/product';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss'],
})
export class BasketComponent implements OnInit {
  path: string = '/checkout';

  basket: BasketProduct = {};
  basketSize: number = 0;

  constructor(router: Router, private basketService: BasketService) {
    //
  }

  ngOnInit(): void {
    this.basket = this.getBasketProducts();
    //
  }

  getBasketProducts(): BasketProduct {
    return this.basketService.getBasketProducts();
  }
}
