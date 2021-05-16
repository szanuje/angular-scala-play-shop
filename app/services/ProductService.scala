package services

import org.mongodb.scala._
import org.mongodb.scala.bson.Document
import org.mongodb.scala.model.Filters

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(implicit ec: ExecutionContext) {

  def findAll(): Future[Seq[Document]] = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("shop")
    val collection: MongoCollection[Document] = database.getCollection("products")
    collection.find().toFuture()
  }

  def findBy(property: String, queryString: String): Future[Seq[Document]] = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("shop")
    val collection: MongoCollection[Document] = database.getCollection("products")
    collection.find(Filters.eq(property, queryString)).toFuture()
  }

  def insert(product: models.Product): Unit = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
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
