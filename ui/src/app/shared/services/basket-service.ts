import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { Product } from 'src/app/_model/Product';
import { UserBasket } from 'src/app/_model/UserBasket';
import 'reflect-metadata';
import { plainToClass } from 'class-transformer';

@Injectable({
  providedIn: 'root',
})
export class BasketService {
  private basket: UserBasket = new UserBasket();
  private basketSubject = new Subject<UserBasket>();

  constructor(private http: HttpClient, private cookieService: CookieService) {
    if(this.cookieService.get('basket') !== '') {
    let parseBasket = JSON.parse(this.cookieService.get('basket'));
      this.basket = plainToClass(UserBasket, parseBasket);
    }
  }

  // getBasket(): Observable<UserBasket> {
  //   if (this.userLoggedIn()) {
  //     return this.fetchUserBasket();
  //   }
  //   else if (this.cookieService.get('basket') !== '') {
  //     return JSON.parse(this.cookieService.get('basket')).asObservable();
  //     //this.basketSubject.next(this.basket);
  //   } else {
  //     return of(new UserBasket());
  //   }  
  // }

  getBasket() {
    return this.basket;
  }

  getBasketSubject() {
    return this.basketSubject;
  }

  initBasketLoggedUser() {
    console.log('sadf')
    this.fetchUserBasket().subscribe(b => {
      this.basket = b;
      //this.basketSubject.next(b);
      console.log('basket', this.basket);
    });
  }

  addProductToBasket(product: Product): void {
    this.basket.addProductToBasket(product);
    this.updateCookie(this.basket);
    this.saveUserBasket();
    //this.basketSubject.next(this.basket);
  }

  updateCookie(basket: UserBasket): void {
    this.cookieService.set('basket', JSON.stringify(basket));
  }

  saveUserBasket(): void {
    if (this.userLoggedIn()) {
      let headers = new HttpHeaders({
        'X-Auth': this.cookieService.get('auth').slice(1, -1),
      });
      let options = { headers: headers };
      this.http
        .put<any>(
          'http://localhost:9000/api/users/' +
            this.cookieService.get('user') +
            '/basket',
          this.basket,
          options
        )
        .subscribe((res) => console.log('updated basket'));
    }
  }

  fetchUserBasket(): Observable<UserBasket> {
    let headers = new HttpHeaders({
      'X-Auth': this.cookieService.get('auth').slice(1, -1),
    });
    let options = { headers: headers };
    return this.http.get<UserBasket>(
      'http://localhost:9000/api/users/' +
        this.cookieService.get('user') +
        '/basket',
      options
    );
  }

  userLoggedIn(): boolean {
    if (this.cookieService.get('user') !== '') {
      return true;
    }
    return false;
  }

}
