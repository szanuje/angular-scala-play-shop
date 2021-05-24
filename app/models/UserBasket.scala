package models

case class UserBasket(
                       private val basketProducts: List[BasketProduct],
                       private val totalPrice: Double
                     ) {
  def getBasketProducts = basketProducts

  def getTotalPrice = totalPrice
}
