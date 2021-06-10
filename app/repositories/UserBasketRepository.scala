package repositories

import models.{Client, UserBasket}
import play.api.Configuration
import reactivemongo.api.bson.BSONDocument

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserBasketRepository @Inject()(implicit ec: ExecutionContext, config: Configuration)
  extends AbstractMongoRepository {

  val USER_EMAIL = "user.email"

  def findUserBasket(email: String): Future[Option[UserBasket]] = {
    clientsCollection
      .flatMap(_
        .find(BSONDocument(USER_EMAIL -> email), Option.empty[UserBasket])
        .one[Client])
      .map(clientOption => clientOption.map(client => client.getUserBasket))
  }

  def updateUserBasket(email: String, userBasket: UserBasket): Unit = {
    clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument(USER_EMAIL -> email),
        u = BSONDocument("$set" -> BSONDocument(
          "userBasket" -> userBasket
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }

  def deleteUserBasket(email: String): Unit = {
    clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument(USER_EMAIL -> email),
        u = BSONDocument("$set" -> BSONDocument(
          "userBasket" -> UserBasket(List(), 0.0)
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }
}
