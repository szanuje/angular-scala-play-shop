package models

import java.util.UUID

case class Cable(uuid: UUID, name: String, length: String) {

  def getUUID = uuid

  def getName = name

  def getLength = length
}
