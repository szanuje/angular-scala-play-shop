import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../_model/product';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  @Input() basket: Product[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
