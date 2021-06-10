import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {

  email: string = '';

  constructor(private cookieService: CookieService) {
    //
  }

  ngOnInit(): void {
    
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    const user = urlParams.get('user');

    if(token != null && user != null) {
      this.saveAuthCookie(token);
      this.saveUserCookie(user);
    }

    this.email = this.cookieService.get('user');
  }

  saveAuthCookie(authString: string) {
    this.cookieService.set('auth', JSON.stringify(authString));
  }

  saveUserCookie(name: string) {
    this.cookieService.set('user', name);
  }

}
