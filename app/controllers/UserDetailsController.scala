package controllers

import models._
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.UserDetailsService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserDetailsController @Inject()(cc: ControllerComponents, userDetailsService: UserDetailsService) extends AbstractController(cc) {

  implicit lazy val userDetailsWrites: OWrites[UserDetails] = Json.writes[UserDetails]
  implicit lazy val userDetailsReads: Reads[UserDetails] = Json.reads[UserDetails]

  def putUserDetails(username: String): Action[UserDetails] = Action.async(parse.json[UserDetails]) { implicit request => {
    val userDetails = request.body
    userDetailsService.updateUserDetails(username, userDetails)
    Future.successful(Created)
  }
  }

  def getUserDetails(username: String): Action[AnyContent] = Action.async { implicit request => {
    userDetailsService.findUserDetails(username)
      .map(res => Ok(Json.toJson(res)))
      .recover(th => BadRequest(th.getMessage))
  }
  }

}
