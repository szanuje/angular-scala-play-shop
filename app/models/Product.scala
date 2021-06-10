package models

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
