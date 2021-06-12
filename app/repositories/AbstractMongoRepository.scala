package repositories

import akka.http.scaladsl.model.DateTime
import models._
import play.api.Configuration
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractMongoRepository(implicit ec: ExecutionContext, config: Configuration) {

  private val MONGO_URL: String = config.underlying.getString("mongodb.uri")
  private val mongoDriver: AsyncDriver = AsyncDriver()
  private lazy val parsedURIFuture: Future[ParsedURI] = MongoConnection.fromString(MONGO_URL)
  private lazy val connection: Future[MongoConnection] = parsedURIFuture.flatMap(u => mongoDriver.connect(u))
  private val db: Future[DB] = connection.flatMap(_.database("shop"))

  def productsCollection: Future[BSONCollection] = {
    db.map(_.collection("products"))
  }

  def clientsCollection: Future[BSONCollection] = {
    db.map(_.collection("clients"))
  }

}
