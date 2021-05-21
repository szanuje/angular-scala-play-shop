import play.api.inject.ApplicationLifecycle
import services.ProductService

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future;


@Singleton
class InitMongoDB @Inject()(lifecycle: ApplicationLifecycle, productService: ProductService) {
  println("xd")
  productService.insert(models.Product("name1", "category1", "description1", 49.99f))
  productService.insert(models.Product("name2", "category1", "description2", 49.99f))
  productService.insert(models.Product("name3", "category1", "description3", 49.99f))
  productService.insert(models.Product("name4", "category1", "description4", 49.99f))
  productService.insert(models.Product("name5", "category2", "description5", 49.99f))
  productService.insert(models.Product("name6", "category3", "description6", 49.99f))
  productService.insert(models.Product("name7", "category4", "description7", 49.99f))
  productService.insert(models.Product("name8", "category4", "description8", 49.99f))

  lifecycle.addStopHook { () =>
    println("stop")
    Future.successful(())
  }
}
