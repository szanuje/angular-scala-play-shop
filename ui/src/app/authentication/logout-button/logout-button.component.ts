import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  styleUrls: ['./logout-button.component.scss'],
})
export class LogoutButtonComponent implements OnInit {
  constructor(private cookieService: CookieService) {
  }

  ngOnInit(): void {
    //
  }

  logout(): void {
    this.cookieService.delete('user');
    this.cookieService.delete('auth');
    window.location.href = '/';
  }
}
