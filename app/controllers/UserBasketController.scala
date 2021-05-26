package controllers

import models._
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.UserBasketService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserBasketController @Inject()(cc: ControllerComponents, userBasketService: UserBasketService)
  extends AbstractController(cc) {

  implicit lazy val userBasketWrites: OWrites[UserBasket] = Json.writes[UserBasket]
  implicit lazy val userBasketReads: Reads[UserBasket] = Json.reads[UserBasket]

  implicit lazy val basketProductWrites: OWrites[BasketProduct] = Json.writes[BasketProduct]
  implicit lazy val basketProductReads: Reads[BasketProduct] = Json.reads[BasketProduct]

  implicit lazy val productWrites: OWrites[Product] = Json.writes[Product]
  implicit lazy val productReads: Reads[Product] = Json.reads[Product]

  def getUserBasket(username: String): Action[AnyContent] = Action.async { implicit request => {
    userBasketService.findUserBasket(username)
      .map(res => Ok(Json.toJson(res)))
      .recover(th => BadRequest(th.getMessage))
  }
  }

  def putUserBasket(username: String): Action[UserBasket] = Action.async(parse.json[UserBasket]) { implicit request => {
    val basketProduct = request.body
    userBasketService.updateUserBasket(username, basketProduct)
    Future.successful(Created)
  }
  }

  def deleteUserBasket(username: String): Action[AnyContent] = Action.async { implicit request => {
    userBasketService.deleteUserBasket(username)
    Future.successful(Ok)
  }
  }
}
