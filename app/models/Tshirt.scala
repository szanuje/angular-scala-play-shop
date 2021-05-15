package models

import java.util.UUID

case class Tshirt(uuid: UUID, brand: String, size: String) {

  def getUUID = uuid

  def getBrand = brand

  def getSize = size
}
