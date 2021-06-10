import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/_model/Product';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {
    //
  }

  fetchProducts() {
    return this.http.get<Product[]>(environment.api_url + '/api/products/all');
  }
}
