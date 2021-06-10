package controllers

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models.User
import play.api.i18n.Lang
import play.api.libs.json.{JsString, Json, OWrites, Reads}
import play.api.mvc.{Action, AnyContent, Request}
import services.UserService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SignUpController @Inject()(cc: SilhouetteControllerComponents, userService: UserService)
  extends SilhouetteController(cc) {

  implicit val userWrites: OWrites[User] = Json.writes[User]
  implicit val userReads: Reads[User] = Json.reads[User]

  def signUp: Action[AnyContent] = unsecuredAction.async { implicit request: Request[AnyContent] =>
    implicit val lang: Lang = supportedLangs.availables.head
    request.body.asJson.flatMap(_.asOpt[User]) match {
      case Some(newUser) if newUser.getPassword.isDefined =>
        userService.retrieve(LoginInfo(CredentialsProvider.ID, newUser.getEmail)).flatMap {
          case Some(_) =>
            Future.successful(Conflict(JsString(messagesApi("user.already.exist"))))
          case None =>
            val loginInfo = LoginInfo(CredentialsProvider.ID, newUser.getEmail)
            val authInfo = passwordHasherRegistry.current.hash(newUser.getPassword.get)
            val user = newUser.copy(password = Some(authInfo.password))
            for {
              _ <- userService.createUser(user)
              authenticator <- authenticatorService.create(loginInfo)
              authInfo <- authInfoRepository.add(loginInfo, authInfo)
              authToken <- authenticatorService.init(authenticator)
            } yield {
              Ok(Json.toJson(user.copy(password = None)))
            }
        }
      case _ => Future.successful(BadRequest(JsString(messagesApi("invalid.body"))))
    }
  }

}
