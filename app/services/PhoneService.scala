package services

import models.Phone
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhoneService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val phones = TableQuery[PhoneTable]

  def findAll(): Future[Seq[Phone]] = db.run {
    phones.result
  }

  def findById(id: UUID): Future[Option[Phone]] = db.run {
    phones.filter(_.uuid === id).result.headOption
  }

  def create(phone: Phone): Unit = db.run {
    phones += phone
  }

  def update(id: UUID, phone: Phone): Future[Int] = db.run {
    phones.filter(_.uuid === id).update(phone)
  }

  def delete(id: UUID): Future[Int] = db.run {
    phones.filter(_.uuid === id).delete
  }

  private class PhoneTable(tag: Tag) extends Table[Phone](tag, "phones") {
    def uuid = column[UUID]("uuid")

    def model = column[String]("model")

    def released = column[Int]("released")

    def memory = column[Int]("memory")

    def ram = column[Int]("ram")

    def * = (uuid, model, released, memory, ram) <> (Phone.tupled, Phone.unapply)
  }
}