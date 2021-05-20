import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../_model/product';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.scss']
})
export class PurchaseComponent implements OnInit {

  basket: Product[] = [
  ];

  products: Product[] = [
    {name: 'name1', description: 'desc1'},
    {name: 'name2', description: 'desc2'},
    {name: 'name3', description: 'desc3'},
    {name: 'name4', description: 'desc4'},
    {name: 'name5', description: 'desc5'},
    {name: 'name6', description: 'desc6'},
    {name: 'name7', description: 'desc1'},
    {name: 'name8', description: 'desc2'},
    {name: 'name9', description: 'desc3'},
    {name: 'name10', description: 'desc4'},
    {name: 'name11', description: 'desc4'},
    {name: 'name12', description: 'desc4'},
    {name: 'name13', description: 'desc4'},
    {name: 'name14', description: 'desc4'},
    {name: 'name15', description: 'desc4'},
    {name: 'name16', description: 'desc4'},
    {name: 'name17', description: 'desc4'},
    {name: 'name18', description: 'desc4'},
    {name: 'name19', description: 'desc4'},
    {name: 'name20', description: 'desc4'},
    {name: 'name21', description: 'desc4'},
    {name: 'name22', description: 'desc4'},
  ];

  constructor(private router: Router) {
  //
  }

  ngOnInit(): void {
  //
  }

  addProductToBasket(product: Product) {
    this.basket = [...this.basket, product];
  }

}
