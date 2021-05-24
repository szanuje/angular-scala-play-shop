package controllers

import models._
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ClientService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ClientController @Inject()(cc: ControllerComponents, clientService: ClientService) extends AbstractController(cc) {

  implicit lazy val clientWrites: OWrites[Client] = Json.writes[Client]
  implicit lazy val clientReads: Reads[Client] = Json.reads[Client]

  implicit lazy val userDetailsWrites: OWrites[UserDetails] = Json.writes[UserDetails]
  implicit lazy val userDetailsReads: Reads[UserDetails] = Json.reads[UserDetails]

  implicit lazy val userBasketWrites: OWrites[UserBasket] = Json.writes[UserBasket]
  implicit lazy val userBasketReads: Reads[UserBasket] = Json.reads[UserBasket]

  implicit lazy val basketProductWrites: OWrites[BasketProduct] = Json.writes[BasketProduct]
  implicit lazy val basketProductReads: Reads[BasketProduct] = Json.reads[BasketProduct]

  implicit lazy val productWrites: OWrites[Product] = Json.writes[Product]
  implicit lazy val productReads: Reads[Product] = Json.reads[Product]

  implicit lazy val userWrites: OWrites[User] = Json.writes[User]
  implicit lazy val userReads: Reads[User] = Json.reads[User]

  def addClient(): Action[Client] = Action.async(parse.json[Client]) { implicit request => {
    val client = request.body
    println("addProduct: " + client)
    clientService.createClient(client)
    Future.successful(Created)
  }
  }

  def getAllClients(): Action[AnyContent] = Action.async { implicit request => {
    clientService.findAllClients()
      .map(res => Ok(Json.toJson(res)))
      .recover(th => BadRequest(th.getMessage))
  }
  }
}
