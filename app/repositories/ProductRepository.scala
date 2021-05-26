package repositories

import models.Product
import play.api.Configuration
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepository @Inject()(implicit ec: ExecutionContext, config: Configuration)
  extends AbstractMongoRepository {

  def createProduct(product: Product): Future[Any] = {
    productsCollection.flatMap { col =>
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
    productsCollection
      .flatMap(_
        .find(BSONDocument())
        .cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]())
      )
  }

  def findProductBy(property: String, queryString: String): Future[List[Product]] = {
    productsCollection
      .flatMap(_.find(BSONDocument(
        property -> queryString
      )).cursor[Product]()
        .collect(err = Cursor.FailOnError[List[Product]]()))
  }
}
