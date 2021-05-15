package models

import java.util.UUID

case class Book(uuid: UUID, title: String, published: Int) {

  def getUUID = uuid

  def getTitle = title

  def getPublished = published
}
