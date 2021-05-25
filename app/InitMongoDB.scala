import models._
import play.api.inject.ApplicationLifecycle
import services._

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future


@Singleton
class InitMongoDB @Inject()(lifecycle: ApplicationLifecycle,
                            clientService: ClientService,
                            productService: ProductService) {

  initDB()

  lifecycle.addStopHook { () =>
    println("stop")
    Future.successful(())
  }

  def initDB(): Unit = {
    for (i <- 0 to 20) {
      productService.createProduct(Product("name" + i, "category" + i % 5, "description" + i, 9.99f))
    }
    val product1 = Product("name1", "category1", "description1", 9.99f)
    val product2 = Product("name2", "category2", "description2", 9.99f)
    val product3 = Product("name3", "category3", "description3", 9.99f)
    val user1 = User("user1", "pass1", "user1@gmail.com")
    val user2 = User("user2", "pass2", "user2@gmail.com")
    val userDetails1 = UserDetails("address1", "city1", "country1", "11-00", "111111111")
    val userDetails2 = UserDetails("address2", "city2", "country2", "22-00", "222222222")
    val userBasket1 = UserBasket(List(
      BasketProduct(product1, 2, 19.98f)),
      19.98f)
    val userBasket2 = UserBasket(List(
      BasketProduct(product1, 2, 19.98f),
      BasketProduct(product2, 1, 9.99f),
      BasketProduct(product3, 1, 9.99f)),
      39.96f)

    clientService.createClient(Client(user1, userDetails1, userBasket1))
    clientService.createClient(Client(user2, userDetails2, userBasket2))
  }

}
