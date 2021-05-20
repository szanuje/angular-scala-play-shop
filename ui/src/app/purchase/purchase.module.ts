import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PurchaseComponent } from './purchase.component';
import { BasketComponent } from './basket/basket.component';
import { ProductsComponent } from './products/products.component';
import { AngularMaterialModule } from '../material.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [PurchaseComponent, BasketComponent, ProductsComponent],
  imports: [CommonModule, AngularMaterialModule, RouterModule],
  exports: [PurchaseComponent, BasketComponent, ProductsComponent],
})
export class PurchaseModule {}
