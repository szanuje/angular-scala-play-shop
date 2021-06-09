import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss'],
})
export class AuthenticationComponent implements OnInit {

  userLoggedIn: boolean = false;

  constructor(private cookieService: CookieService) {}

  ngOnInit(): void {
    if(this.cookieService.get('user') !== '') {
      this.userLoggedIn = true;
    } else {
      this.userLoggedIn = false;
    }
  }
}
