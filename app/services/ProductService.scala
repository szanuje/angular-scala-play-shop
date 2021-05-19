package services

import org.mongodb.scala._
import org.mongodb.scala.bson.Document
import org.mongodb.scala.model.Filters
import play.api.Configuration

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(implicit ec: ExecutionContext, config: Configuration) {

  val host = config.underlying.getString("MONGO_H")
  val MONGO_URL = "mongodb://" + host + ":27017"

  def findAll(): Future[Seq[Document]] = {
    println(host)
    val mongoClient: MongoClient = MongoClient(MONGO_URL)
    val database: MongoDatabase = mongoClient.getDatabase("shop")
    val collection: MongoCollection[Document] = database.getCollection("products")
    collection.find().toFuture()
  }

  def findBy(property: String, queryString: String): Future[Seq[Document]] = {
    val mongoClient: MongoClient = MongoClient(MONGO_URL)
    val database: MongoDatabase = mongoClient.getDatabase("shop")
    val collection: MongoCollection[Document] = database.getCollection("products")
    collection.find(Filters.eq(property, queryString)).toFuture()
  }

  def insert(product: models.Product): Unit = {
    val mongoClient: MongoClient = MongoClient(MONGO_URL)
    val database: MongoDatabase = mongoClient.getDatabase("shop")
    val collection: MongoCollection[Document] = database.getCollection("products")
    val doc: Document = Document(
      "name" -> product.getName,
      "category" -> product.getCategory,
      "description" -> product.getDescription
    )
    collection
      .insertOne(doc)
      .toFuture()
  }

}
