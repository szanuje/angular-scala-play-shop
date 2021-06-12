package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class BasketProduct(
                          private val product: Product,
                          private val quantity: Int,
                          private val totalPrice: Double
                        ) {
  def getProduct = product

  def getQuantity = quantity

  def getTotalPrice = totalPrice
}

object BasketProduct {
  implicit def basketProductWriter: BSONDocumentWriter[BasketProduct] = Macros.writer[BasketProduct]

  implicit def basketProductReader: BSONDocumentReader[BasketProduct] = Macros.reader[BasketProduct]
}
