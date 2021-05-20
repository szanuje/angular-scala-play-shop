import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { BasketProduct } from '../../_model/basket';
import { BasketService } from 'src/app/shared/services/basket-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class CheckoutComponent implements OnInit {
  basket: BasketProduct = {};
  columnsToDisplay = ['name', 'category', 'quantity', 'price'];
  expandedElement: BasketProduct | null = null;
  totalPrice: number = 0;

  constructor(private router: Router, private basketService: BasketService, private _snackBar: MatSnackBar) {}
  
  ngOnInit(): void {
    this.basket = this.getBasketProducts();
    this.totalPrice = this.getBasketValue();
  }

  getBasketProducts(): BasketProduct {
    return this.basketService.getBasketProducts();
  }

  getBasketValue(): number {
    let totalPrice = 0
    for(const [key, val] of Object.entries(this.basket)) {
      totalPrice += val.totalPrice;
    }
    return +totalPrice.toFixed(2);
  }

  openSnackBar() {
    this._snackBar.open('Success!', 'Close');
  }
}
