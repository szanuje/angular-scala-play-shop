import { Component, OnInit } from '@angular/core';
import { BasketService } from 'src/app/shared/services/basket-service';
import { ProductService } from 'src/app/shared/services/product-service';
import { Product } from '../../_model/Product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  productCategories: Set<string> = new Set(['none']);
  currentCategory: string = 'none';

  selectedProduct: Product | null = null;
  selectedProductHtmlId: string = 'default';
  selectedProductHtmlIdPrefix: string = 'product';
  isProductSelected: boolean = false;

  pageSizes: number[] = [6, 12];
  numberOfColumns: 2 | 3 = 2;

  currentPageSize: 6 | 12 = 6;
  currentPageIndex: number = 0;

  constructor(
    private productService: ProductService,
    private basketService: BasketService
  ) {
    //
  }

  ngOnInit() {
    this.getProducts();
  }

  getProducts() {
    this.productService.fetchProducts().subscribe((res) => {
      this.products = res;
      this.extractProductsCategories();
      this.filterProductsByCategory(this.currentCategory);
    });
  }

  filterProductsByCategory(category: string) {
    if (category === 'none') {
      this.filteredProducts = this.products;
      return;
    }
    this.currentCategory = category;
    this.filteredProducts = this.products.filter(prod => prod.category === category);
  }

  onPaginateChange(event: any) {
    console.log(event);
    this.currentPageIndex = event.pageIndex;
    this.currentPageSize = event.pageSize;
    this.rebuildPage(this.currentPageIndex, this.currentPageSize);
  }

  rebuildPage(pageIndex: number, pageSize: number) {
    if (pageSize === 6) this.numberOfColumns = 2;
    if (pageSize === 12) this.numberOfColumns = 3;
  }

  addProductToBasket(product: Product | null) {
    if (product !== null) {
      this.basketService.addProductToBasket(product);
      this.removeSelection();
    }
  }

  selectProduct(product: Product, productHtmlId: number) {
    let productId = this.selectedProductHtmlIdPrefix + productHtmlId;
    this.removeSelection();
    this.addSelection(productId, product);
  }

  private removeSelection() {
    document
      .getElementById(this.selectedProductHtmlId)
      ?.classList.remove('selected-product');
    this.isProductSelected = false;
    this.selectedProduct = null;
  }

  private addSelection(productId: string, newProduct: Product) {
    document.getElementById(productId)?.classList.add('selected-product');
    this.isProductSelected = true;
    this.selectedProductHtmlId = productId;
    this.selectedProduct = newProduct;
  }

  private extractProductsCategories() {
    for (let product of this.products) {
      this.productCategories.add(product.category);
    }
  }
}
