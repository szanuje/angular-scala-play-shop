package services

import models.Tv
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TvService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                         (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  private val tvs = TableQuery[TvTable]

  def findAll(): Future[Seq[Tv]] = db.run {
    tvs.result
  }

  def findById(id: UUID): Future[Option[Tv]] = db.run {
    tvs.filter(_.uuid === id).result.headOption
  }

  def create(tv: Tv): Unit = db.run {
    tvs += tv
  }

  def update(id: UUID, tv: Tv): Future[Int] = db.run {
    tvs.filter(_.uuid === id).update(tv)
  }

  def delete(id: UUID): Future[Int] = db.run {
    tvs.filter(_.uuid === id).delete
  }

  private class TvTable(tag: Tag) extends Table[Tv](tag, "tvs") {
    def uuid = column[UUID]("uuid")

    def model = column[String]("model")

    def size = column[Int]("sizeinch")

    def * = (uuid, model, size) <> (Tv.tupled, Tv.unapply)
  }
}