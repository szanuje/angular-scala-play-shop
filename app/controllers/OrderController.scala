package controllers

import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models.UserDetails
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.Action
import services.OrderService
import utils.auth.{JWTEnvironment, WithProvider}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class OrderController @Inject()(ssc: SilhouetteControllerComponents, orderService: OrderService)
  extends SilhouetteController(ssc) {

  implicit lazy val userDetailsWrites: OWrites[UserDetails] = Json.writes[UserDetails]
  implicit lazy val userDetailsReads: Reads[UserDetails] = Json.reads[UserDetails]

  def placeOrder(email: String): Action[UserDetails] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID)).async(parse.json[UserDetails]) { implicit request => {
      orderService.placeOrder(email, request.body)
      Future.successful(Created.withHeaders(("access-control-expose-headers", "X-Auth")))
    }
    }
}
