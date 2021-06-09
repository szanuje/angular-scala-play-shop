package repositories

import models.{Client, UserDetails}
import play.api.Configuration
import reactivemongo.api.bson.BSONDocument

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserDetailsRepository @Inject()(implicit ec: ExecutionContext, config: Configuration)
  extends AbstractMongoRepository {

  def findUserDetails(email: String): Future[Option[UserDetails]] = {
    clientsCollection
      .flatMap(_
        .find(BSONDocument("user.email" -> email), Option.empty[UserDetails])
        .one[Client])
      .map(clientOption => clientOption.map(client => client.getUserDetails))
  }

  def updateUserDetails(email: String, userDetails: UserDetails): Unit = {
    clientsCollection.flatMap(col => {
      val updateBuilder = col.update(true)
      val updates = updateBuilder.element(
        q = BSONDocument("user.email" -> email),
        u = BSONDocument("$set" -> BSONDocument(
          "userDetails" -> userDetails
        )),
        multi = true,
        upsert = true
      )
      updates.flatMap(updateEle => updateBuilder.many(Seq(updateEle)))
    })
  }
}
