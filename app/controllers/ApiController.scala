package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}



@Singleton
class ApiController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                             (implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def ping = Action { implicit request =>
    Ok("PONG!")
  }

  def book = Action { implicit request => {
    val books = TableQuery[BookTable]
//    val res = DBIO.seq(
//      books.result
//    )
//    println(books.length.toString())
//    println(res)
//    db.run(res)
//
//    db.run(books.result).andThen(r => r.foreach(u => println(u)))
    db.run(books.result).map(r => r.foreach(v => println(v)))
    Ok("sdfasdfsaa")
  }
  }


  case class Book(name: String, asd: String)

  class BookTable(tag: Tag) extends Table[Book](tag, "books") {
    def name = column[String]("name")
    def asd = column[String]("asd")

    def * = (name, asd) <> (Book.tupled, Book.unapply)
  }
}