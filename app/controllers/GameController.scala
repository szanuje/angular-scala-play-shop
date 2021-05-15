package controllers

import models.Game
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.GameService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GameController @Inject()(cc: ControllerComponents, gameService: GameService) extends AbstractController(cc) {
  implicit val gameWrites: OWrites[Game] = Json.writes[Game]
  implicit val gameReads: Reads[Game] = Json.reads[Game]

  def getGames: Action[AnyContent] = Action.async { implicit request => {
    gameService.findAll()
      .map(res => {
        println("getGames: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getGameById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    gameService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getGameById(" + id + "): not exists")
          BadRequest
        } else {
          println("getGameById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addGame(): Action[Game] = Action.async(parse.json[Game]) { implicit request => {
    val game = request.body
    println("addGame: " + game)
    gameService.findById(game.uuid)
      .flatMap(_game => {
        if (_game.isEmpty) {
          println("game added")
          gameService.create(game)
          Future.successful(Created)
        } else {
          println("game already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateGame(id: UUID): Action[Game] = Action.async(parse.json[Game]) { implicit request => {
    val game = request.body
    println("updateGame: " + game)
    gameService.update(id, game)
    Future.successful(Created)
  }
  }

  def deleteGame(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteGame(" + id + ")")
    gameService.delete(id)
    Future.successful(NoContent)
  }
  }
}