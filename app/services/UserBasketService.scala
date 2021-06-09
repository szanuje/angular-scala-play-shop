package services

import models.UserBasket
import repositories.UserBasketRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserBasketService @Inject()(implicit ec: ExecutionContext, userBasketRepository: UserBasketRepository) {

  def findUserBasket(email: String): Future[Option[UserBasket]] = {
    userBasketRepository.findUserBasket(email)
  }

  def updateUserBasket(email: String, userBasket: UserBasket): Unit = {
    userBasketRepository.updateUserBasket(email, userBasket)
  }

  def deleteUserBasket(email: String): Unit = {
    userBasketRepository.deleteUserBasket(email)
  }
}
