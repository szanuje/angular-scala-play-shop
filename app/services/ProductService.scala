package services

import models.Product
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}
import repository.MongoRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(implicit ec: ExecutionContext, mongoRepository: MongoRepository) {

  implicit def productWriter: BSONDocumentWriter[Product] = Macros.writer[Product]

  implicit def productReader: BSONDocumentReader[Product] = Macros.reader[Product]

  def createProduct(product: Product): Future[Any] = {
    mongoRepository.productsCollection.flatMap { col =>
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

  def findAllProducts(): Future[List[Product]] = {
    mongoRepository.productsCollection
      .flatMap(_
        .find(BSONDocument())
        .cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]())
      )
  }

  def findProductBy(property: String, queryString: String): Future[List[Product]] = {
    mongoRepository.productsCollection
      .flatMap(_.find(BSONDocument(
        property -> queryString
      )).cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]()))
  }
}
