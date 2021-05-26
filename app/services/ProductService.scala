package services

import models.Product
import repositories.ProductRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(implicit ec: ExecutionContext, productRepository: ProductRepository) {

  def createProduct(product: Product): Future[Any] = {
    productRepository.createProduct(product)
  }

  def findAllProducts(): Future[List[Product]] = {
    productRepository.findAllProducts()
  }

  def findProductBy(property: String, queryString: String): Future[List[Product]] = {
    productRepository.findProductBy(property, queryString)
  }
}
