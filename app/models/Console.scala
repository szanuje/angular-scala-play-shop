package models

import java.util.UUID

case class Console(uuid: UUID, model: String) {

  def getUUID = uuid

  def getModel = model
}
