package utils

import models._
import play.api.inject.ApplicationLifecycle
import services._

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class InitMongoDB @Inject()(lifecycle: ApplicationLifecycle, productService: ProductService) {

  initDB()

  lifecycle.addStopHook { () =>
    println("stop")
    Future.successful(())
  }

  def initDB(): Unit = {
    for (i <- 0 to 30) {
      productService.createProduct(Product("Product" + i, "category" + i % 5, "Product description here..." + i, 9.99f))
    }
  }

}
