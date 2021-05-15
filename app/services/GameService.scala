package services

import models.Game
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GameService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val games = TableQuery[GameTable]

  def findAll(): Future[Seq[Game]] = db.run {
    games.result
  }

  def findById(id: UUID): Future[Option[Game]] = db.run {
    games.filter(_.uuid === id).result.headOption
  }

  def create(game: Game): Unit = db.run {
    games += game
  }

  def update(id: UUID, game: Game): Future[Int] = db.run {
    games.filter(_.uuid === id).update(game)
  }

  def delete(id: UUID): Future[Int] = db.run {
    games.filter(_.uuid === id).delete
  }

  private class GameTable(tag: Tag) extends Table[Game](tag, "games") {
    def uuid = column[UUID]("uuid")

    def title = column[String]("model")

    def released = column[Int]("released")

    def category = column[String]("category")

    def * = (uuid, title, released, category) <> (Game.tupled, Game.unapply)
  }
}