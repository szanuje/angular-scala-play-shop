package services

import models.{UserDetails, UserOrder}
import repositories.OrderRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderService @Inject()(implicit ec: ExecutionContext,
                             orderRepository: OrderRepository,
                             userDetailsService: UserDetailsService,
                             userBasketService: UserBasketService) {

  def placeOrder(email: String, userDetails: UserDetails): Future[Any] = {
    val basketFuture = userBasketService.findUserBasket(email)
    userBasketService.deleteUserBasket(email)
    userDetailsService.updateUserDetails(email, userDetails)
    basketFuture.flatMap(basket =>
      orderRepository.createOrder(email, UserOrder(basket.get)))
  }
}
