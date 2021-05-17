import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Product } from '../../_model/product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements OnInit {

  @Output() addProductToBasketEvent = new EventEmitter<Product>();
  @Input() products: Product[] = [];

  selectedProduct: Product | null = null;
  selectedProductHtmlId: string = 'default';
  selectedProductHtmlIdPrefix: string = 'product';
  isProductSelected: boolean = false;

  pageSizes: number[] = [6, 12];
  numberOfColumns: 2 | 3 = 2;

  currentPageSize: 6 | 12 = 6;
  currentPageIndex: number = 0;

  constructor() { }

  ngOnInit(): void { }

  onPaginateChange(event: any) {
    console.log(event);
    this.currentPageIndex = event.pageIndex;
    this.currentPageSize = event.pageSize;
    this.rebuildPage(this.currentPageIndex, this.currentPageSize);
  }

  rebuildPage(pageIndex: number, pageSize: number) {
    if(pageSize === 6) this.numberOfColumns = 2;
    if(pageSize === 12) this.numberOfColumns = 3;
  }

  addProductToBasket(product: Product | null) {
    if(product !== null) {
      this.addProductToBasketEvent.emit(product);
      this.removeSelection();
    }
  }

  selectProduct(product: Product, productHtmlId: number) {
    let productId = this.selectedProductHtmlIdPrefix + productHtmlId;
    this.removeSelection();
    this.addSelection(productId, product);
  }

  removeSelection() {
    document
    .getElementById(this.selectedProductHtmlId)
    ?.classList.remove('selected-product');
    this.isProductSelected = false;
    this.selectedProduct = null;
  }

  addSelection(productId: string, newProduct: Product) {
    document
      .getElementById(productId)
      ?.classList.add('selected-product');
      this.isProductSelected = true;
    this.selectedProductHtmlId = productId;
    this.selectedProduct = newProduct;
  }
}
