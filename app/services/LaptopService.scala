package services

import models.Laptop
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class LaptopService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                             (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val laptops = TableQuery[LaptopTable]

  def findAll(): Future[Seq[Laptop]] = db.run {
    laptops.result
  }

  def findById(id: UUID): Future[Option[Laptop]] = db.run {
    laptops.filter(_.uuid === id).result.headOption
  }

  def create(laptop: Laptop): Unit = db.run {
    laptops += laptop
  }

  def update(id: UUID, laptop: Laptop): Future[Int] = db.run {
    laptops.filter(_.uuid === id).update(laptop)
  }

  def delete(id: UUID): Future[Int] = db.run {
    laptops.filter(_.uuid === id).delete
  }

  private class LaptopTable(tag: Tag) extends Table[Laptop](tag, "laptops") {
    def uuid = column[UUID]("uuid")

    def model = column[String]("model")

    def released = column[Int]("released")

    def memory = column[Int]("memory")

    def ram = column[Int]("ram")

    def graphics = column[String]("graphics")

    def cpu = column[String]("cpu")

    def * = (uuid, model, released, memory, ram, graphics, cpu) <> (Laptop.tupled, Laptop.unapply)
  }
}