package models

import java.util.UUID

case class Tv(uuid: UUID, model: String, sizeInch: Int) {

  def getUUID = uuid

  def getModel = model

  def getSizeInch = sizeInch
}
