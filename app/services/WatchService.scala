package services

import models.Watch
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class WatchService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val watches = TableQuery[WatchTable]

  def findAll(): Future[Seq[Watch]] = db.run {
    watches.result
  }

  def findById(id: UUID): Future[Option[Watch]] = db.run {
    watches.filter(_.uuid === id).result.headOption
  }

  def create(watch: Watch): Unit = db.run {
    watches += watch
  }

  def update(id: UUID, watch: Watch): Future[Int] = db.run {
    watches.filter(_.uuid === id).update(watch)
  }

  def delete(id: UUID): Future[Int] = db.run {
    watches.filter(_.uuid === id).delete
  }

  private class WatchTable(tag: Tag) extends Table[Watch](tag, "watches") {
    def uuid = column[UUID]("uuid")

    def brand = column[String]("brand")

    def diameter = column[Int]("diameter")

    def * = (uuid, brand, diameter) <> (Watch.tupled, Watch.unapply)
  }
}