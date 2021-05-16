package controllers

import models.Book
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{BookService, ProductService}

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class BookController @Inject()(cc: ControllerComponents, bookService: BookService) extends AbstractController(cc) {
  implicit val bookWrites: OWrites[Book] = Json.writes[Book]
  implicit val bookReads: Reads[Book] = Json.reads[Book]

  def getBooks: Action[AnyContent] = Action.async { implicit request => {
    bookService.findAll()
      .map(res => {
        println("getBooks: " + res)
        Ok(Json.toJson(res.toList))
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def getBookById(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    bookService.findById(id)
      .map(res => {
        if (res.isEmpty) {
          println("getBookById(" + id + "): not exists")
          BadRequest
        } else {
          println("getBookById: " + res)
          Ok(Json.toJson(res))
        }
      })
      .recover(th => Ok(th.getMessage))
  }
  }

  def addBook(): Action[Book] = Action.async(parse.json[Book]) { implicit request => {
    val book = request.body
    println("addBook: " + book)
    bookService.findById(book.uuid)
      .flatMap(_book => {
        if (_book.isEmpty) {
          println("book added")
          bookService.create(book)
          Future.successful(Created)
        } else {
          println("book already exists")
          Future.successful(NoContent)
        }
      })
  }
  }

  def updateBook(id: UUID): Action[Book] = Action.async(parse.json[Book]) { implicit request => {
    val book = request.body
    println("updateBook: " + book)
    bookService.update(id, book)
    Future.successful(Created)
  }
  }

  def deleteBook(id: UUID): Action[AnyContent] = Action.async { implicit request => {
    println("deleteBook(" + id + ")")
    bookService.delete(id)
    Future.successful(NoContent)
  }
  }
}
