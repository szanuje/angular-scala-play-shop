package services

import models.UserBasket
import repositories.UserBasketRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserBasketService @Inject()(implicit ec: ExecutionContext, userBasketRepository: UserBasketRepository) {

  def findUserBasket(username: String): Future[Option[UserBasket]] = {
    userBasketRepository.findUserBasket(username)
  }

  def updateUserBasket(username: String, userBasket: UserBasket): Unit = {
    userBasketRepository.updateUserBasket(username, userBasket)
  }

  def deleteUserBasket(username: String): Unit = {
    userBasketRepository.deleteUserBasket(username)
  }
}
