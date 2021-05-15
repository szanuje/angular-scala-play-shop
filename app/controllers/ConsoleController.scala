package controllers

import models.Console
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ConsoleService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ConsoleController @Inject()(cc: ControllerComponents, consoleService: ConsoleService) extends AbstractController(cc) {
  implicit val consoleWrites: OWrites[Console] = Json.writes[Console]
  implicit val consoleReads: Reads[Console] = Json.reads[Console]

  def getConsoles: Action[AnyContent] = Action.async { implicit request => {
    consoleService.findAll()
      .map(res => {
        println("getConsoles: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getConsoleById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    consoleService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getConsoleById(" + id + "): not exists")
          BadRequest
        } else {
          println("getConsoleById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addConsole(): Action[Console] = Action.async(parse.json[Console]) { implicit request => {
    val console = request.body
    println("addConsole: " + console)
    consoleService.findById(console.uuid)
      .flatMap(_console => {
        if (_console.isEmpty) {
          println("console added")
          consoleService.create(console)
          Future.successful(Created)
        } else {
          println("console already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateConsole(id: UUID): Action[Console] = Action.async(parse.json[Console]) { implicit request => {
    val console = request.body
    println("updateConsole: " + console)
    consoleService.update(id, console)
    Future.successful(Created)
  }
  }

  def deleteConsole(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteConsole(" + id + ")")
    consoleService.delete(id)
    Future.successful(NoContent)
  }
  }
}