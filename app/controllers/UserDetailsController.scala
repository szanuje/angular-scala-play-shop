package controllers

import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models._
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{Action, AnyContent}
import services.UserDetailsService
import utils.auth.{JWTEnvironment, WithProvider}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserDetailsController @Inject()(ssc: SilhouetteControllerComponents, userDetailsService: UserDetailsService)
  extends SilhouetteController(ssc) {

  implicit lazy val userDetailsWrites: OWrites[UserDetails] = Json.writes[UserDetails]
  implicit lazy val userDetailsReads: Reads[UserDetails] = Json.reads[UserDetails]

  def putUserDetails(email: String): Action[UserDetails] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID))
      .async(parse.json[UserDetails]) { implicit request => {
        val userDetails = request.body
        userDetailsService.updateUserDetails(email, userDetails)
        Future.successful(Created)
      }
      }

  def getUserDetails(email: String): Action[AnyContent] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID)).async { implicit request => {
      userDetailsService.findUserDetails(email)
        .map(res => Ok(Json.toJson(res))) //
    }
    }

}
