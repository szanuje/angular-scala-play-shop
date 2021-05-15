package services

import models.Shoes
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShoesService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val shoes = TableQuery[ShoesTable]

  def findAll(): Future[Seq[Shoes]] = db.run {
    shoes.result
  }

  def findById(id: UUID): Future[Option[Shoes]] = db.run {
    shoes.filter(_.uuid === id).result.headOption
  }

  def create(shoe: Shoes): Unit = db.run {
    shoes += shoe
  }

  def update(id: UUID, shoe: Shoes): Future[Int] = db.run {
    shoes.filter(_.uuid === id).update(shoe)
  }

  def delete(id: UUID): Future[Int] = db.run {
    shoes.filter(_.uuid === id).delete
  }

  private class ShoesTable(tag: Tag) extends Table[Shoes](tag, "shoes") {
    def uuid = column[UUID]("uuid")

    def brand = column[String]("brand")

    def size = column[Int]("size")

    def * = (uuid, brand, size) <> (Shoes.tupled, Shoes.unapply)
  }
}