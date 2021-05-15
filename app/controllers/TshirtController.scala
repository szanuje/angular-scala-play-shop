package controllers

import models.Tshirt
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.TshirtService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TshirtController @Inject()(cc: ControllerComponents, tshirtService: TshirtService) extends AbstractController(cc) {
  implicit val tshirtWrites: OWrites[Tshirt] = Json.writes[Tshirt]
  implicit val tshirtReads: Reads[Tshirt] = Json.reads[Tshirt]

  def getTshirts: Action[AnyContent] = Action.async { implicit request => {
    tshirtService.findAll()
      .map(res => {
        println("getTshirts: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getTshirtById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    tshirtService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getTshirtById(" + id + "): not exists")
          BadRequest
        } else {
          println("getTshirtById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addTshirt(): Action[Tshirt] = Action.async(parse.json[Tshirt]) { implicit request => {
    val tshirt = request.body
    println("addTshirt: " + tshirt)
    tshirtService.findById(tshirt.uuid)
      .flatMap(_tshirt => {
        if (_tshirt.isEmpty) {
          println("tshirt added")
          tshirtService.create(tshirt)
          Future.successful(Created)
        } else {
          println("tshirt already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateTshirt(id: UUID): Action[Tshirt] = Action.async(parse.json[Tshirt]) { implicit request => {
    val tshirt = request.body
    println("updateTshirt: " + tshirt)
    tshirtService.update(id, tshirt)
    Future.successful(Created)
  }
  }

  def deleteTshirt(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteTshirt(" + id + ")")
    tshirtService.delete(id)
    Future.successful(NoContent)
  }
  }
}