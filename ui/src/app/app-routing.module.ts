import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckoutComponent } from './purchase/checkout/checkout.component';
import { PurchaseComponent } from './purchase/purchase.component';

const routes: Routes = [
  { path: 'home', component: PurchaseComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: '**', component: PurchaseComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
