import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-button',
  templateUrl: './register-button.component.html',
  styleUrls: ['./register-button.component.scss'],
})
export class RegisterButtonComponent implements OnInit {

  registerLink: string = '/login';

  constructor(private router: Router) {}

  ngOnInit(): void {
    //
  }

}
