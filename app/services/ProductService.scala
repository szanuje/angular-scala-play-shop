package services

import models.Product
import play.api.Configuration
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(implicit ec: ExecutionContext, config: Configuration) {
  val MONGO_URL: String = config.underlying.getString("mongodb.uri")

  val mongoDriver: AsyncDriver = AsyncDriver()
  lazy val parsedURIFuture: Future[ParsedURI] = MongoConnection.fromString(MONGO_URL)
  lazy val connection: Future[MongoConnection] = parsedURIFuture.flatMap(u => mongoDriver.connect(u))
  val db: Future[DB] = connection.flatMap(_.database("shop"))
  val products: Future[BSONCollection] = db.map(_.collection("products"))

  implicit def moviesWriter: BSONDocumentWriter[models.Product] = Macros.writer[models.Product]

  implicit def moviesReader: BSONDocumentReader[models.Product] = Macros.reader[models.Product]

  def insert(product: models.Product): Future[Any] = {
    products.flatMap { col =>
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("name" -> product.getName),
        u = BSONDocument("$set" -> BSONDocument(
          "name" -> product.getName,
          "category" -> product.getCategory,
          "description" -> product.getDescription,
          "price" -> product.getPrice
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    }
  }

  def findAll(): Future[List[Product]] = {
    println(MONGO_URL)
    products
      .flatMap(_.find(BSONDocument()).cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]()))
  }

  def findBy(property: String, queryString: String): Future[List[Product]] = {
    products
      .flatMap(_.find(BSONDocument(
        property -> queryString
      )).cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]()))
  }

}
