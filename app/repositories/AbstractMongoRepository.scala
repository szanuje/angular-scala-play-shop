package repositories

import models.{BasketProduct, Client, Product, User, UserBasket, UserDetails}
import play.api.Configuration
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractMongoRepository(implicit ec: ExecutionContext, config: Configuration) {

  private val MONGO_URL: String = config.underlying.getString("mongodb.uri")
  private val mongoDriver: AsyncDriver = AsyncDriver()
  private lazy val parsedURIFuture: Future[ParsedURI] = MongoConnection.fromString(MONGO_URL)
  private lazy val connection: Future[MongoConnection] = parsedURIFuture.flatMap(u => mongoDriver.connect(u))
  private val db: Future[DB] = connection.flatMap(_.database("shop"))

  implicit def clientWriter: BSONDocumentWriter[Client] = Macros.writer[Client]

  implicit def clientReader: BSONDocumentReader[Client] = Macros.reader[Client]

  implicit def userWriter: BSONDocumentWriter[User] = Macros.writer[User]

  implicit def userReader: BSONDocumentReader[User] = Macros.reader[User]

  implicit def userDetailsWriter: BSONDocumentWriter[UserDetails] = Macros.writer[UserDetails]

  implicit def userDetailsReader: BSONDocumentReader[UserDetails] = Macros.reader[UserDetails]

  implicit def userBasketWriter: BSONDocumentWriter[UserBasket] = Macros.writer[UserBasket]

  implicit def userBasketReader: BSONDocumentReader[UserBasket] = Macros.reader[UserBasket]

  implicit def basketProductWriter: BSONDocumentWriter[BasketProduct] = Macros.writer[BasketProduct]

  implicit def basketProductReader: BSONDocumentReader[BasketProduct] = Macros.reader[BasketProduct]

  implicit def productWriter: BSONDocumentWriter[Product] = Macros.writer[Product]

  implicit def productReader: BSONDocumentReader[Product] = Macros.reader[Product]

  def productsCollection: Future[BSONCollection] = {
    db.map(_.collection("products"))
  }

  def clientsCollection: Future[BSONCollection] = {
    db.map(_.collection("clients"))
  }

}
