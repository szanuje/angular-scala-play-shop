package models

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class Product(
                    private val name: String,
                    private val category: String,
                    private val description: String,
                    private val price: BigDecimal
                  ) {

  def getName = name

  def getCategory = category

  def getDescription = description

  def getPrice = price
}

object Product {
  implicit def productWriter: BSONDocumentWriter[Product] = Macros.writer[Product]

  implicit def productReader: BSONDocumentReader[Product] = Macros.reader[Product]
}