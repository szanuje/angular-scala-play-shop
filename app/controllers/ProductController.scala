package controllers

import models.Product
import org.mongodb.scala.bson.Document
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
      .map(res => {
        println("getBooks: " + res)
        Ok(jsonizeDocs(res.toList))
      })
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
        Ok(jsonizeDocs(res.toList))
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
  //
  //  def updateProduct(id: UUID): Action[Product] = Action.async(parse.json[Product]) { implicit request => {
  //    val product = request.body
  //    println("updateProduct: " + product)
  //    mongoService.update(id, product)
  //    Future.successful(Created)
  //  }
  //  }
  //
  //  def deleteProduct(id: UUID): Action[AnyContent] = Action.async { implicit request => {
  //    println("deleteProduct(" + id + ")")
  //    mongoService.delete(id)
  //    Future.successful(NoContent)
  //  }
  //  }

  def jsonizeDocs(cDocument: Seq[Document]): String = {
    val sb = new StringBuilder
    for (doc <- cDocument) {
      if (sb.nonEmpty) {
        sb.append(",")
      }
      sb.append(doc.toJson())
    }
    sb.toString
  }
}