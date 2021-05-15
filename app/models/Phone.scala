package models

import java.util.UUID

case class Phone(uuid: UUID, model: String, released: Int, memory: Int, ram: Int) {

  def getUUID = uuid

  def getModel = model

  def getReleased = released

  def getMemory = memory

  def getRam = ram
}
