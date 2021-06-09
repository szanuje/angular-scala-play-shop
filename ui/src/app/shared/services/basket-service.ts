import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable, Subject } from 'rxjs';
import { Product } from 'src/app/_model/Product';
import { UserBasket } from 'src/app/_model/UserBasket';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class BasketService {
  private basket: UserBasket;
  private basketObservable: Subject<UserBasket>;

  constructor(private http: HttpClient, private cookieService: CookieService) {

    this.basket = new UserBasket();
    console.log(this.basket);

    this.basketObservable = new Subject<UserBasket>();
    if(this.userLoggedIn()) {
      this.fetchUserBasket().subscribe(
        basket => {
        console.log('loaded1');

        console.log(basket);
        console.log('loaded2');

        this.basketObservable.next(basket);

      });
    }
    console.log(this.basket);
  }

  addProductToBasket(product: Product) {
    this.basket.addProductToBasket(product);
    this.basketObservable.next(this.basket);
    this.updateCookie(this.basket);
    this.saveUserBasket();
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
    this.cookieService.set('basket', JSON.stringify(basket));
  }

  saveUserBasket() {
    if(this.userLoggedIn()) {
      let headers = new HttpHeaders({
        'X-Auth': this.cookieService.get('auth').slice(1,-1),
      });
      let options = { headers: headers };
      this.http.put<any>('http://localhost:9000/api/users/' + this.cookieService.get('user') + '/basket', this.basket, options)
       .subscribe(res => console.log('updated basket'));
    }
  }

  userLoggedIn() {
    if(this.cookieService.get("user") !== '') {
      return true;
    }
    return false;
  }

  fetchUserBasket(): Observable<UserBasket> {
    let headers = new HttpHeaders({
      'X-Auth': this.cookieService.get('auth').slice(1,-1),
    });
    let options = { headers: headers };
    return this.http.get<UserBasket>('http://localhost:9000/api/users/' + this.cookieService.get('user') + '/basket', options)
    }
}
