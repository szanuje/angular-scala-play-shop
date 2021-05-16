package models

case class Product(name: String, category: String, description: String) {

  def getName = name

  def getCategory = category

  def getDescription = description
}
