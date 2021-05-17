import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginButtonComponent } from './login-button/login-button.component';
import { AngularMaterialModule } from '../material.module';
import { RegisterButtonComponent } from './register-button/register-button.component';
import { LogoutButtonComponent } from './logout-button/logout-button.component';
import { AuthenticationComponent } from './authentication.component';



@NgModule({
  declarations: [
    LoginButtonComponent,
    RegisterButtonComponent,
    LogoutButtonComponent,
    AuthenticationComponent
  ],
  imports: [
    CommonModule,
    AngularMaterialModule
  ],
  exports: [
    LoginButtonComponent,
    RegisterButtonComponent,
    LogoutButtonComponent,
    AuthenticationComponent
  ]
})
export class AuthenticationModule { }
