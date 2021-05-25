import { Component, OnInit } from '@angular/core';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { BasketService } from 'src/app/shared/services/basket-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BasketProduct } from 'src/app/_model/BasketProduct';
import { UserBasket } from 'src/app/_model/UserBasket';

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
  basket: UserBasket;
  columnsToDisplay = ['name', 'category', 'quantity', 'price'];
  expandedElement: BasketProduct | null = null;

  constructor(
    private basketService: BasketService,
    private _snackBar: MatSnackBar
  ) {
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

  openSnackBar() {
    this._snackBar.open('Success!', 'Close');
  }
}
