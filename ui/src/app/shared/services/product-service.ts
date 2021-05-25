import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/_model/Product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {
    //
  }

  fetchProducts() {
    return this.http.get<Product[]>('http://localhost:9000/api/products/all');
  }
}
