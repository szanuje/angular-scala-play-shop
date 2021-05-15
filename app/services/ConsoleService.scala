package services

import models.Console
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ConsoleService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val consoles = TableQuery[ConsoleTable]

  def findAll(): Future[Seq[Console]] = db.run {
    consoles.result
  }

  def findById(id: UUID): Future[Option[Console]] = db.run {
    consoles.filter(_.uuid === id).result.headOption
  }

  def create(console: Console): Unit = db.run {
    consoles += console
  }

  def update(id: UUID, console: Console): Future[Int] = db.run {
    consoles.filter(_.uuid === id).update(console)
  }

  def delete(id: UUID): Future[Int] = db.run {
    consoles.filter(_.uuid === id).delete
  }

  private class ConsoleTable(tag: Tag) extends Table[Console](tag, "consoles") {
    def uuid = column[UUID]("uuid")

    def model = column[String]("model")

    def * = (uuid, model) <> (Console.tupled, Console.unapply)
  }
}