import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnChanges, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
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

  constructor(router: Router, private basketService: BasketService, private http: HttpClient, private cookieService: CookieService) {
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

  saveUserBasket() {
    if(this.userLoggedIn()) {
      let headers = new HttpHeaders({
        'X-Auth': this.cookieService.get('auth').slice(1,-1),
      });
      let options = { headers: headers };
      this.http.put<any>('http://localhost:9000/api/users/' + this.cookieService.get('user') + '/basket',
       this.basket, options)
       .subscribe(res => console.log(res));
    }
  }

  userLoggedIn() {
    if(this.cookieService.get("user") !== '') {
      return true;
    }
    return false;
  }
}
