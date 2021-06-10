package controllers

import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models._
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{Action, AnyContent}
import services.UserBasketService
import utils.auth.{JWTEnvironment, WithProvider}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserBasketController @Inject()(ssc: SilhouetteControllerComponents, userBasketService: UserBasketService)
  extends SilhouetteController(ssc) {

  implicit lazy val userBasketWrites: OWrites[UserBasket] = Json.writes[UserBasket]
  implicit lazy val userBasketReads: Reads[UserBasket] = Json.reads[UserBasket]

  implicit lazy val basketProductWrites: OWrites[BasketProduct] = Json.writes[BasketProduct]
  implicit lazy val basketProductReads: Reads[BasketProduct] = Json.reads[BasketProduct]

  implicit lazy val productWrites: OWrites[Product] = Json.writes[Product]
  implicit lazy val productReads: Reads[Product] = Json.reads[Product]

  def getUserBasket(email: String): Action[AnyContent] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID)).async { implicit request => {
      userBasketService.findUserBasket(email)
        .map(res => Ok(Json.toJson(res))) //
    }
    }

  def putUserBasket(email: String): Action[UserBasket] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID)).async(parse.json[UserBasket]) { implicit request => {
      val basketProduct = request.body
      userBasketService.updateUserBasket(email, basketProduct)
      Future.successful(Created.withHeaders(("access-control-expose-headers", "X-Auth")))
    }
    }

  def deleteUserBasket(email: String): Action[AnyContent] =
    securedAction(WithProvider[JWTEnvironment#A](CredentialsProvider.ID)).async { implicit request => {
      userBasketService.deleteUserBasket(email)
      Future.successful(Ok)
    }
    }
}
