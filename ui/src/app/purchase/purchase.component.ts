import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasketService } from '../shared/services/basket-service';
import { BasketProduct } from '../_model/basket';
import { Product } from '../_model/product';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.scss'],
})
export class PurchaseComponent implements OnInit {
  basket: BasketProduct = {};
  basketSize: number = 0;

  products: Product[] = [
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name2', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name3', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name4', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name5', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name6', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name7', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name8', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name9', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
    { name: 'name1', category: 'cat', description: 'desc1', price: 20.2 },
  ];

  constructor(private router: Router, private basketService: BasketService) {
    //
  }

  ngOnInit(): void {
    //
  }

  addProductToBasket(product: Product) {
    this.basketService.addProductToBasket(product);
    console.log('added');
  }
}
