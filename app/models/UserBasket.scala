package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class UserBasket(
                       private val basketProducts: List[BasketProduct],
                       private val totalPrice: BigDecimal
                     ) {
  def getBasketProducts = basketProducts

  def getTotalPrice = totalPrice
}

object UserBasket {
  implicit def userBasketWriter: BSONDocumentWriter[UserBasket] = Macros.writer[UserBasket]

  implicit def userBasketReader: BSONDocumentReader[UserBasket] = Macros.reader[UserBasket]
}
