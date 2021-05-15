package models

import java.util.UUID

case class Shoes(uuid: UUID, brand: String, size: Int) {

  def getUUID = uuid

  def getBrand = brand

  def getSize = size
}
