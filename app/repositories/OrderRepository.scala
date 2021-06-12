package repositories

import models.UserOrder
import play.api.Configuration
import reactivemongo.api.bson.BSONDocument

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(implicit ec: ExecutionContext, config: Configuration)
  extends AbstractMongoRepository {

  def createOrder(email: String, order: UserOrder): Future[Any] = {
    clientsCollection.flatMap { col =>
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.email" -> email),
        u = BSONDocument("$push" -> BSONDocument(
          "userOrders" -> order
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    }
  }
}
