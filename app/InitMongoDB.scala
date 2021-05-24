import models._
import play.api.inject.ApplicationLifecycle
import services._

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future


@Singleton
class InitMongoDB @Inject()(lifecycle: ApplicationLifecycle, clientService: ClientService) {

  clientService.createClient(Client(
    User("user2", "pass1", "user1@gmail.com"),
    UserDetails("address1", "city1", "country1", "00-00", "9999999999"),
    UserBasket(
      List(
        BasketProduct(
          Product("name1", "category1", "description1", 49.99f),
          2,
          99.98f
        )
      ),
      99.9f
    )
  ))

  clientService.createClient(Client(
    User("user1", "pass1", "user1@gmail.com"),
    UserDetails("address1", "city1", "country1", "00-00", "9999999999"),
    UserBasket(
      List(
        BasketProduct(
          Product("name1", "category1", "description1", 49.99f),
          2,
          99.98f
        )
      ),
      99.9f
    )
  ))

  lifecycle.addStopHook { () =>
    println("stop")
    Future.successful(())
  }
}
