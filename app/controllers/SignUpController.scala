package controllers

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models.User
import play.api.i18n.Lang
import play.api.libs.json.{JsString, Json, OWrites, Reads}
import play.api.mvc.{Action, AnyContent}
import services.UserService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SignUpController @Inject()(cc: SilhouetteControllerComponents, userService: UserService)
  extends SilhouetteController(cc) {

  implicit val userWrites: OWrites[User] = Json.writes[User]
  implicit val userReads: Reads[User] = Json.reads[User]

  def signUp(): Action[User] = UnsecuredAction.async(parse.json[User]) { implicit request => {
    implicit val lang: Lang = supportedLangs.availables.head
    val user = request.body

    userService.retrieve(LoginInfo(CredentialsProvider.ID, user.getName))
      .flatMap {
        case Some(_) =>
          Future.successful(Conflict(JsString(messagesApi("user.already.exists"))))
        case None =>
          val authInfo = passwordHasherRegistry.current.hash(user.getPassword)
          val newUser = user.copy(password = authInfo.password)
          userService.createUser(newUser)
          Future.successful(Ok(Json.toJson(newUser.copy(password = ""))))
      }
  }
  }

}
