import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

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
export class LoginPageComponent {

  nameLFormControl = new FormControl('', [
    Validators.required
  ]);

  passwordLFormControl = new FormControl('', [
    Validators.required
  ]);

  emailLFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  emailRFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  nameRFormControl = new FormControl('', [
    Validators.required
  ]);

  passwordRFormControl = new FormControl('', [
    Validators.required
  ]);
  
  loginForm = new FormGroup({
    email: this.emailLFormControl,
    password: this.passwordLFormControl
  });

  registerForm = new FormGroup({
    name: this.nameRFormControl,
    password: this.passwordRFormControl,
    email: this.emailRFormControl
  });

  matcher = new MyErrorStateMatcher();

  constructor(private http: HttpClient, private cookieService: CookieService) {
    //
  }

  headers = new HttpHeaders({
    'Access-Control-Expose-Headers': 'x-total-count',
  });
  options = { headers: this.headers };

  onLoginSubmit() {
    console.log(this.loginForm.value);
    this.http.post<any>(environment.api_url + '/api/login', this.loginForm.value, {observe: 'response'})
    .subscribe((res: HttpResponse<any>) => {
      let myHeader = res.headers.get('X-Auth');
      if(myHeader !== null) {
        this.saveAuthCookie(myHeader);
        this.saveUserCookie(this.loginForm.value.email);
        document.location.href = '/';
      }
      console.log(myHeader);
      return res;
  });
  }

  onRegisterSubmit() {
    console.log(this.registerForm.value);
    this.http.post<any>(environment.api_url + '/api/register', this.registerForm.value, {observe: 'response'})
    .subscribe(res => console.log(res));
  }

  loginWithGoogle() {
    window.location.href = environment.api_url + '/authenticate/google';
    console.log(window.location);
  }

  saveAuthCookie(authString: string) {
      this.cookieService.set( 'auth', JSON.stringify(authString));
  }

  saveUserCookie(name: string) {
    this.cookieService.set('user', name);
  }

}
