package models

case class BasketProduct(
                          private val product: Product,
                          private val quantity: Int,
                          private val totalPrice: Double
                        ) {
  def getProduct = product

  def getQuantity = quantity

  def getTotalPrice = totalPrice
}
