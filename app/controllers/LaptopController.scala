package controllers

import models.Laptop
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.LaptopService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class LaptopController @Inject()(cc: ControllerComponents, laptopService: LaptopService) extends AbstractController(cc) {
  implicit val laptopWrites: OWrites[Laptop] = Json.writes[Laptop]
  implicit val laptopReads: Reads[Laptop] = Json.reads[Laptop]

  def getLaptops: Action[AnyContent] = Action.async { implicit request => {
    laptopService.findAll()
      .map(res => {
        println("getLaptops: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getLaptopById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    laptopService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getLaptopById(" + id + "): not exists")
          BadRequest
        } else {
          println("getLaptopById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addLaptop(): Action[Laptop] = Action.async(parse.json[Laptop]) { implicit request => {
    val laptop = request.body
    println("addLaptop: " + laptop)
    laptopService.findById(laptop.uuid)
      .flatMap(_laptop => {
        if (_laptop.isEmpty) {
          println("laptop added")
          laptopService.create(laptop)
          Future.successful(Created)
        } else {
          println("laptop already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateLaptop(id: UUID): Action[Laptop] = Action.async(parse.json[Laptop]) { implicit request => {
    val laptop = request.body
    println("updateLaptop: " + laptop)
    laptopService.update(id, laptop)
    Future.successful(Created)
  }
  }

  def deleteLaptop(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteLaptop(" + id + ")")
    laptopService.delete(id)
    Future.successful(NoContent)
  }
  }
}