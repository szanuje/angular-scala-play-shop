package controllers

import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.Credentials
import play.api.i18n.Lang
import play.api.libs.json.{JsString, Json, OFormat}
import play.api.mvc.Action

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SignInController @Inject()(ssc: SilhouetteControllerComponents) extends SilhouetteController(ssc) {

  case class SignInModel(username: String, password: String)

  implicit val signInFormat: OFormat[SignInModel] = Json.format[SignInModel]

  def signIn(): Action[SignInModel] = UnsecuredAction.async(parse.json[SignInModel]) { implicit request => {
    implicit val lang: Lang = supportedLangs.availables.head
    val signInModel = request.body
    val credentials = Credentials(signInModel.username, signInModel.password)
    credentialsProvider.authenticate(credentials).flatMap { loginInfo =>
      userService.retrieve(loginInfo).flatMap {
        case Some(_) =>
          for {
            authenticator <- authenticatorService.create(loginInfo)
            token <- authenticatorService.init(authenticator)
            result <- authenticatorService.embed(token, Ok)
          } yield {
            logger.debug(s"user ${loginInfo.providerKey} signed successfully")
            result
          }
        case None => Future.successful(BadRequest(JsString(messagesApi("could.not.find.user"))))
      }
    }.recover {
      case _: ProviderException => BadRequest(JsString(messagesApi("invalid.credentials")))
    }
  }

  }

}
