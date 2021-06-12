import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { UserDetails } from "src/app/_model/UserDetails";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root',
  })
export class OrderService {

    constructor(private http: HttpClient, private cookieService: CookieService) {
    }

    placeOrder(userDetails: UserDetails) {
        let headers = new HttpHeaders({
            'X-Auth': this.cookieService.get('auth').slice(1, -1),
          });
          let options = { headers: headers };
        this.http.post<any>(environment.api_url + '/api/users/' + this.cookieService.get('user') + '/order', userDetails, options)
        .subscribe(res => console.log(res));
    }
}