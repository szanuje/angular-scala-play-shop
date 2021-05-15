package controllers

import models.Cable
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.CableService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class CableController @Inject()(cc: ControllerComponents, cableService: CableService) extends AbstractController(cc) {
  implicit val cableWrites: OWrites[Cable] = Json.writes[Cable]
  implicit val cableReads: Reads[Cable] = Json.reads[Cable]

  def getCables: Action[AnyContent] = Action.async { implicit request => {
    cableService.findAll()
      .map(res => {
        println("getCables: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getCableById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    cableService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getCableById(" + id + "): not exists")
          BadRequest
        } else {
          println("getCableById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addCable(): Action[Cable] = Action.async(parse.json[Cable]) { implicit request => {
    val cable = request.body
    println("addCable: " + cable)
    cableService.findById(cable.uuid)
      .flatMap(_cable => {
        if (_cable.isEmpty) {
          println("cable added")
          cableService.create(cable)
          Future.successful(Created)
        } else {
          println("cable already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateCable(id: UUID): Action[Cable] = Action.async(parse.json[Cable]) { implicit request => {
    val cable = request.body
    println("updateCable: " + cable)
    cableService.update(id, cable)
    Future.successful(Created)
  }
  }

  def deleteCable(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteCable(" + id + ")")
    cableService.delete(id)
    Future.successful(NoContent)
  }
  }
}