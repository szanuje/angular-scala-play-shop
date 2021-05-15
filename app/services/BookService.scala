package services

import models.Book
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class BookService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val books = TableQuery[BookTable]

  def findAll(): Future[Seq[Book]] = db.run {
    books.result
  }

  def findById(id: UUID): Future[Option[Book]] = db.run {
    books.filter(_.uuid === id).result.headOption
  }

  def create(book: Book): Unit = db.run {
    books += book
  }

  def update(id: UUID, book: Book): Future[Int] = db.run {
    books.filter(_.uuid === id).update(book)
  }

  def delete(id: UUID): Future[Int] = db.run {
    books.filter(_.uuid === id).delete
  }

  private class BookTable(tag: Tag) extends Table[Book](tag, "books") {
    def uuid = column[UUID]("uuid")

    def title = column[String]("title")

    def published = column[Int]("published")

    def * = (uuid, title, published) <> (Book.tupled, Book.unapply)
  }
}