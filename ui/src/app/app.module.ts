import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AngularMaterialModule } from './material.module';
import { MenuComponent } from './home/menu/menu.component';
import { AuthModule } from '@auth0/auth0-angular';
import { environment as env } from '../environments/environment';
import { PurchaseModule } from './purchase/purchase.module';
import { CommonModule } from '@angular/common';
import { AuthenticationModule } from './authentication/authentication.module';
import { BasketService } from './shared/services/basket-service';
import { ProductService } from './shared/services/product-service';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { LoginPageComponent } from './authentication/login-page/login-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    AngularMaterialModule,
    PurchaseModule,
    AuthenticationModule,
    HttpClientModule,
    AuthModule.forRoot({
      ...env.auth,
    })
  ],
  providers: [BasketService, ProductService, CookieService],
  bootstrap: [AppComponent],
})
export class AppModule {}
