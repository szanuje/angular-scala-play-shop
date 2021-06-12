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
import { CookieService } from 'ngx-cookie-service';
import { OrderService } from 'src/app/shared/services/order-service';
import { UserDetails } from 'src/app/_model/UserDetails';

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
    private _snackBar: MatSnackBar,
    private cookieService: CookieService,
    private orderService: OrderService
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

  placeOrder(userDetails: UserDetails) {
    console.log(userDetails);
    if( this.userLoggedIn() ) {
      this.orderService.placeOrder(userDetails);
    }
  }

  openSnackBar(): void {
    this._snackBar.open('Success!', 'Close');
  }

  userLoggedIn(): boolean {
    if (this.cookieService.get('user') !== '') {
      return true;
    }
    return false;
  }
}
