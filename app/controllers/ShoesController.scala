package controllers

import models.Shoes
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ShoesService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ShoesController @Inject()(cc: ControllerComponents, shoesService: ShoesService) extends AbstractController(cc) {
  implicit val shoesWrites: OWrites[Shoes] = Json.writes[Shoes]
  implicit val shoesReads: Reads[Shoes] = Json.reads[Shoes]

  def getShoes: Action[AnyContent] = Action.async { implicit request => {
    shoesService.findAll()
      .map(res => {
        println("getShoess: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getShoesById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    shoesService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getShoesById(" + id + "): not exists")
          BadRequest
        } else {
          println("getShoesById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addShoes(): Action[Shoes] = Action.async(parse.json[Shoes]) { implicit request => {
    val shoes = request.body
    println("addShoes: " + shoes)
    shoesService.findById(shoes.uuid)
      .flatMap(_shoes => {
        if (_shoes.isEmpty) {
          println("shoes added")
          shoesService.create(shoes)
          Future.successful(Created)
        } else {
          println("shoes already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateShoes(id: UUID): Action[Shoes] = Action.async(parse.json[Shoes]) { implicit request => {
    val shoes = request.body
    println("updateShoes: " + shoes)
    shoesService.update(id, shoes)
    Future.successful(Created)
  }
  }

  def deleteShoes(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteShoes(" + id + ")")
    shoesService.delete(id)
    Future.successful(NoContent)
  }
  }
}