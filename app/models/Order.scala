package models

import akka.http.scaladsl.model.DateTime

case class Order(
                  private val userBasket: UserBasket,
                ) {
  private val createdAt: DateTime = DateTime.now

  def getUserBasket = userBasket

  def getCreatedAt = createdAt
}
