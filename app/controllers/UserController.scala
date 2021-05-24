package controllers

import models.User
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ClientService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserController @Inject()(cc: ControllerComponents, clientService: ClientService) extends AbstractController(cc) {

  implicit val userWrites: OWrites[User] = Json.writes[User]
  implicit val userReads: Reads[User] = Json.reads[User]

  def getUser(username: String): Action[AnyContent] = Action.async { implicit request => {
    clientService.findUser(username)
      .map(res => {
        Ok(Json.toJson(res))
      })
      .recover(th => BadRequest(th.getMessage))
  }
  }

  def postUser(): Action[User] = Action.async(parse.json[User]) { implicit request => {
    val user = request.body
    clientService.createUser(user)
    Future.successful(Ok)
  }
  }

  def putUser(username: String): Action[User] = Action.async(parse.json[User]) { implicit request => {
    val user = request.body
    println("addUser: " + user)
    clientService.updateUser(username, user)
    Future.successful(Created)
  }
  }

}
