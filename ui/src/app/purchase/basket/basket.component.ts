import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../../_model/product';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  path: string = '/checkout';

  @Input() basket: Product[] = [];

  constructor(router: Router) {
  //
  }

  ngOnInit(): void {
  //
  }

}
