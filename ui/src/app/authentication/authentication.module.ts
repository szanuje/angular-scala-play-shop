import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginButtonComponent } from './login-button/login-button.component';
import { AngularMaterialModule } from '../material.module';
import { RegisterButtonComponent } from './register-button/register-button.component';
import { LogoutButtonComponent } from './logout-button/logout-button.component';
import { AuthenticationComponent } from './authentication.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    LoginButtonComponent,
    RegisterButtonComponent,
    LogoutButtonComponent,
    AuthenticationComponent,
    LoginPageComponent
  ],
  imports: [CommonModule, AngularMaterialModule, RouterModule],
  exports: [
    LoginButtonComponent,
    RegisterButtonComponent,
    LogoutButtonComponent,
    AuthenticationComponent,
    LoginPageComponent
  ],
})
export class AuthenticationModule {}
