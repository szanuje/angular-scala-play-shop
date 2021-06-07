import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Subject } from 'rxjs';
import { Product } from 'src/app/_model/Product';
import { UserBasket } from 'src/app/_model/UserBasket';

@Injectable({
  providedIn: 'root',
})
export class BasketService {
  private basket: UserBasket;
  private basketObservable: Subject<UserBasket>;

  constructor(private http: HttpClient, private cookieService: CookieService) {
    this.basket = new UserBasket();
    this.basketObservable = new Subject<UserBasket>();
    this.basketObservable.next(this.basket);
  }

  addProductToBasket(product: Product) {
    this.basket.addProductToBasket(product);
    this.basketObservable.next(this.basket);
    this.updateCookie(this.basket);
  }

  getBasketObservable() {
    return this.basketObservable;
  }

  getBasket() {
    return this.basket;
  }

  fetchBasket() {
    this.http.get('localhost:9000/api/users/user1/basket');
  }

  updateCookie(basket: UserBasket) {
    this.cookieService.set( 'basket', JSON.stringify(basket));
  }
}
