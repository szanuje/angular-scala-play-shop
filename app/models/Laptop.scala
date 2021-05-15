package models

import java.util.UUID

case class Laptop(uuid: UUID, model: String, released: Int, memory: Int, ram: Int, graphics: String, cpu: String) {

  def getUUID = uuid

  def getModel = model

  def getReleased = released

  def getMemory = memory

  def getRam = ram

  def getGraphics = graphics

  def getCpu = cpu
}
