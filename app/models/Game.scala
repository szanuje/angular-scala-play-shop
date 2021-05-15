package models

import java.util.UUID

case class Game(uuid: UUID, title: String, released: Int, category: String) {

  def getUUID = uuid

  def getTitle = title

  def getReleased = released

  def getCategory = category
}
