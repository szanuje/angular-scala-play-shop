package controllers

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton
class ApiController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)
                             (implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def ping = Action { implicit request =>
    Ok("PONG!")
  }

  def book = Action { implicit request => {
    //    db.run(DBIO.seq(
    //      sqlu"""drop table IF EXISTS suppliers """,
    //      sqlu"""create table suppliers(id int primary key, name varchar(16) not null) """
    //    ))
    val books = TableQuery[BookTable]
    //    val res = DBIO.seq(
    //      books.result
    //    )
    //    println(books.length.toString())
    //    println(res)
    //    db.run(res)
    //
    //    db.run(books.result).andThen(r => r.foreach(u => println(u)))
    db.run(TableQuery[BookTable] += (Book(5, "title5", 2000)))

    db.run(books.result).map(r => r.foreach(v => println(v)))
    Ok("sdfasdfsaa")
  }
  }


  case class Book(id: Int, title: String, published: Int)

  class BookTable(tag: Tag) extends Table[Book](tag, "books") {
    def id = column[Int]("id")

    def title = column[String]("title")

    def published = column[Int]("published")

    def * = (id, title, published) <> (Book.tupled, Book.unapply)
  }
}