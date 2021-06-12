package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import java.time.Instant

case class UserOrder(
                      private val userBasket: UserBasket,
                      private val createdAt: Long = Instant.now().getEpochSecond
                    ) {

  def getUserBasket = userBasket

  def getCreatedAt = createdAt
}

object UserOrder {
  implicit def userOrdersWriter: BSONDocumentWriter[UserOrder] = Macros.writer[UserOrder]

  implicit def userOrdersReader: BSONDocumentReader[UserOrder] = Macros.reader[UserOrder]
}
