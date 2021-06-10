package controllers

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.impl.providers._
import models.User
import play.api.mvc.{Action, AnyContent, Cookie, Request}
import services.UserService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SocialAuthController @Inject()(scc: DefaultSilhouetteControllerComponents,
                                     configuration: play.api.Configuration,
                                     userService: UserService)(implicit ex: ExecutionContext)
  extends SilhouetteController(scc) {

  def authenticate(provider: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    (socialProviderRegistry.get[SocialProvider](provider) match {
      case Some(p: SocialProvider with CommonSocialProfileBuilder) =>
        p.authenticate().flatMap {
          case Left(result) => Future.successful(result)
          case Right(authInfo) => for {
            profile <- p.retrieveProfile(authInfo)
            loginInfo = LoginInfo(profile.loginInfo.providerID, profile.email.get)
            _ <- userService.retrieve(loginInfo).flatMap {
              case None =>
                userService.createUser(User(profile.email.get, None, profile.firstName.get))
              case _ => Future.successful(())
            }
            authenticator <- authenticatorService.create(loginInfo)
            authToken <- authenticatorService.init(authenticator)
            result <- authenticatorService.embed(
              authToken,
              Redirect(configuration.underlying.getString("hosts.client") +
                "/sso?user=" + profile.firstName.get + "&token=" + authToken))
          } yield {
            logger.debug(s"User ${profile.loginInfo.providerKey} signed success")
            result.withCookies(Cookie("X-Auth", authToken, httpOnly = false))
          }
        }
      case _ => Future.failed(new ProviderException(s"Cannot authenticate with unexpected social provider $provider"))
    }).recover {
      case e: ProviderException =>
        logger.error("Unexpected provider error", e)
        Forbidden("Forbidden")
    }
  }
}