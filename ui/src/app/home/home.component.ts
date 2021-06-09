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
    this.email = this.cookieService.get('user');
  }

}
