import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { User } from 'src/app/_model/User';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  usernameFormControl = new FormControl('', [
    Validators.required
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required
  ]);
  
  loginForm = new FormGroup({
    username: this.usernameFormControl,
    password: this.passwordFormControl
  }
  )

  matcher = new MyErrorStateMatcher();

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  onLoginSubmit() {
    console.log(this.loginForm.value);
    this.http.post<any>("localhost:9000/api/login", this.loginForm.value).subscribe(res => console.log(res.headers.get('X-Auth')));
  }

}
