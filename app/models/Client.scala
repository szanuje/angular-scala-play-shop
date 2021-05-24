package models

case class Client(
                   private val user: User,
                   private val userDetails: UserDetails,
                   //private val userOrders: Order,
                   private val userBasket: UserBasket
                 ) {
  def getUser = user

  def getUserDetails = userDetails

  //def getUserOrders = userOrders

  def getUserBasket = userBasket
}
