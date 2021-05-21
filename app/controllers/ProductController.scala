package controllers

import models.Product
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ProductService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ProductController @Inject()(cc: ControllerComponents, productService: ProductService) extends AbstractController(cc) {

  implicit val phoneWrites: OWrites[Product] = Json.writes[Product]
  implicit val phoneReads: Reads[Product] = Json.reads[Product]

  def getProducts: Action[AnyContent] = Action.async { implicit request => {
    productService.findAll()
      .map(res => Ok(Json.toJson(res)))
      .recover(th => BadRequest(th.getMessage))
  }
  }

  def getProductsBy(queryString: Option[String]): Action[AnyContent] = Action.async { implicit request => {
    println(request.queryString)
    println(request.rawQueryString)
    val vals = request.rawQueryString.split("=")
    val key = vals(0)
    val value = vals(1)
    println("query by " + key + ":" + value)
    productService.findBy(key, value)
      .map(res => {
        Ok(Json.toJson(res))
      })
      .recover(th => BadRequest(th.getMessage))
  }
  }

  def addProduct(): Action[Product] = Action.async(parse.json[Product]) { implicit request => {
    val product = request.body
    println("addProduct: " + product)
    productService.insert(product)
    Future.successful(Created)
  }
  }

}