package services

import models.Tshirt
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TshirtService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                             (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val tshirts = TableQuery[TshirtTable]

  def findAll(): Future[Seq[Tshirt]] = db.run {
    tshirts.result
  }

  def findById(id: UUID): Future[Option[Tshirt]] = db.run {
    tshirts.filter(_.uuid === id).result.headOption
  }

  def create(tshirt: Tshirt): Unit = db.run {
    tshirts += tshirt
  }

  def update(id: UUID, tshirt: Tshirt): Future[Int] = db.run {
    tshirts.filter(_.uuid === id).update(tshirt)
  }

  def delete(id: UUID): Future[Int] = db.run {
    tshirts.filter(_.uuid === id).delete
  }

  private class TshirtTable(tag: Tag) extends Table[Tshirt](tag, "tshirts") {
    def uuid = column[UUID]("uuid")

    def brand = column[String]("brand")

    def size = column[String]("siz")

    def * = (uuid, brand, size) <> (Tshirt.tupled, Tshirt.unapply)
  }
}