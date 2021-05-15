package models

import java.util.UUID

case class Watch(uuid: UUID, brand: String, diameter: Int) {

  def getUUID = uuid

  def getBrand = brand

  def getDiameter = diameter
}
