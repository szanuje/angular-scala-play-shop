package services

import models.Cable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CableService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val cables = TableQuery[CableTable]

  def findAll(): Future[Seq[Cable]] = db.run {
    cables.result
  }

  def findById(id: UUID): Future[Option[Cable]] = db.run {
    cables.filter(_.uuid === id).result.headOption
  }

  def create(cable: Cable): Unit = db.run {
    cables += cable
  }

  def update(id: UUID, cable: Cable): Future[Int] = db.run {
    cables.filter(_.uuid === id).update(cable)
  }

  def delete(id: UUID): Future[Int] = db.run {
    cables.filter(_.uuid === id).delete
  }

  private class CableTable(tag: Tag) extends Table[Cable](tag, "cables") {
    def uuid = column[UUID]("uuid")

    def name = column[String]("name")

    def length = column[String]("length")

    def * = (uuid, name, length) <> (Cable.tupled, Cable.unapply)
  }
}