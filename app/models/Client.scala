package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class Client(
                   private val user: User,
                   private val userDetails: UserDetails,
                   private val userOrders: List[UserOrder],
                   private val userBasket: UserBasket
                 ) {
  def getUser = user

  def getUserDetails = userDetails

  def getUserOrders = userOrders

  def getUserBasket = userBasket
}

object Client {
  implicit def clientWriter: BSONDocumentWriter[Client] = Macros.writer[Client]

  implicit def clientReader: BSONDocumentReader[Client] = Macros.reader[Client]
}